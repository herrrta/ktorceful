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
class UserRoute: Base<User, Int>() {

    override suspend fun post(call: RoutingCall, klass: KClass<User>) {
        val user = call.receive(klass)
        UserRepository.insert(user)
        call.respond(HttpStatusCode.OK)
    }

    override suspend fun get(call: RoutingCall) {
        call.respond(UserRepository.users)
    }

    override suspend fun put(call: RoutingCall, pk: Int, klass: KClass<User>) {
        val user = call.receive(klass)
        UserRepository.update(user.copy(id = pk))

        call.respond(HttpStatusCode.OK)
    }

    override suspend fun delete(call: RoutingCall, pk: Int) {
        val deleted = UserRepository.delete(pk)
        if (deleted)
            return call.respond(HttpStatusCode.OK)

        call.respond(HttpStatusCode.BadRequest, "Entity not found!")

    }

    @Action
    private suspend fun deactivate(call: RoutingCall, entities: List<User>) {
        entities.forEach {
            UserRepository.update(it.copy(active = false))
        }

        call.respond(HttpStatusCode.OK)
    }

    @Action(name = "DEACTIVATE_USERS")
    private suspend fun sameAsDeactivate(call: RoutingCall, entities: List<User>) {
        deactivate(call, entities)
    }

    override suspend fun getInstance(pk: Int): User? = UserRepository.get(pk)
}