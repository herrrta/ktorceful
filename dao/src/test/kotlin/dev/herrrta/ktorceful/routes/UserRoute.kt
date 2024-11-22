package dev.herrrta.ktorceful.routes

import dev.herrrta.ktorceful.dao.annotations.Action
import dev.herrrta.ktorceful.data.entity.User
import dev.herrrta.ktorceful.data.repository.UserRepository
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import kotlin.reflect.KClass

@Resource("user")
class UserRoute: Base<User>() {
    override val klass: KClass<User> by lazy { User::class }

    override suspend fun post(call: RoutingCall) {
        val user = call.receive<User>()
        UserRepository.insert(user)
        call.respond(HttpStatusCode.OK)
    }

    override suspend fun get(call: RoutingCall) {
        call.parameters["pk"] ?: return call.respond(UserRepository.users)
        super.get(call)
    }

    override suspend fun put(call: RoutingCall) {
        val user = call.receive<User>()
        UserRepository.update(user)

        call.respond(HttpStatusCode.OK)
    }

    override suspend fun delete(call: RoutingCall) {
        call.parameters["pk"]?.toInt()
            ?.let { UserRepository.delete(it) }
            ?: return call.respond(HttpStatusCode.BadRequest, "Entity not found!")

        call.respond(HttpStatusCode.OK)
    }

    @Action
    private suspend fun deactivate(call: RoutingCall, entities: List<User>) {
        entities.forEach {
            UserRepository.update(it.copy(active = false))
        }

        call.respond(HttpStatusCode.OK)
    }

    override suspend fun getInstance(pk: String): User? = UserRepository.get(pk.toInt())
}