package dev.herrrta.ktorceful.user

import dev.herrrta.ktorceful.core.interfaces.Delete
import dev.herrrta.ktorceful.core.interfaces.Get
import dev.herrrta.ktorceful.core.interfaces.Post
import dev.herrrta.ktorceful.user.UserRepository.users
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.RoutingCall

@Resource("user")
class UserRoute: Get, Post, Delete {
    override suspend fun get(call: RoutingCall) {
        call.respond(users)
    }

    override suspend fun post(call: RoutingCall) {
        val user = call.receive<User>()

        users.add(user)

        call.respondText { "Added $user!" }
    }

    override suspend fun delete(call: RoutingCall) {
        val index = call.queryParameters["index"]?.toInt()
            ?: return call.respondText { "Missing param 'index'" }

        val user = users.removeAt(index)

        call.respondText { "Removed $user!" }
    }
}