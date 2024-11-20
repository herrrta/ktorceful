package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.dao.annotations.Action
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import kotlin.reflect.KParameter
import kotlin.reflect.KSuspendFunction2
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.jvm.isAccessible

private val PARAMS = listOf(
    RoutingCall::class.starProjectedType.classifier,
    List::class.starProjectedType.classifier
)
internal typealias ActionFunctionType<E> = KSuspendFunction2<RoutingCall, List<E>, Unit>

interface EntityAction<E: Any>: EntityRoute<E> {
    suspend fun getActions(
        call: RoutingCall
    ): Map<String, ActionFunctionType<E>> {
        return this::class.memberFunctions.fold(mutableMapOf()) { acc, kFunction ->
            kFunction.findAnnotation<Action>() ?: return@fold acc

            val params = kFunction.parameters
                .filter { it.kind == KParameter.Kind.VALUE }
                .map { it.type.classifier }

            params.size == PARAMS.size || error(
                "${this::class}.${kFunction.name} has wrong parameter size!"
            )

            PARAMS.withIndex().all { (index, param) -> param == params[index] } || error(
                "${this::class}.${kFunction.name} has the wrong parameters!"
            )

            acc[kFunction.name] = kFunction as ActionFunctionType<E>
            acc
        }
    }
}

suspend inline fun <reified E : Any> EntityAction<E>.invokeAction(
    call: RoutingCall,
    name: String
) {
    val entities = call.receive<List<E>>()

    if (entities.isEmpty())
        return call.respond(HttpStatusCode.BadRequest)

    getActions(call)[name]?.apply {
        isAccessible = true
        callSuspend(this@invokeAction, call, entities)
    } ?: throw NotImplementedError("unknown action: $name")
}