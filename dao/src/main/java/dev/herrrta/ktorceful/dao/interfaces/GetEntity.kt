package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.core.interfaces.Get
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.full.starProjectedType

interface GetEntity<E : Any> : Get, EntityRoute<E> {
    override suspend fun get(call: RoutingCall) {
        val pk = call.parameters["pk"]
        val entity = pk?.let { getInstance(it) } ?: return call.respond(HttpStatusCode.BadRequest)

        call.respond(
            entity,
            TypeInfo(klass, klass.starProjectedType)
        )
    }

    suspend fun getInstance(pk: String): E?
}