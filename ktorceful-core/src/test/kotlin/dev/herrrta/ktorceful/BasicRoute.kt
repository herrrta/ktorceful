package dev.herrrta.ktorceful

import dev.herrrta.ktorceful.core.interfaces.Get
import dev.herrrta.ktorceful.core.interfaces.Post
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall

@Resource("/basic")
class BasicRoute: Get, Post {
    companion object {
        const val GET_RESPONSE_TEST = "GET Test response"
        const val POST_RESPONSE_TEST = "POST Test response"
    }

    override suspend fun get(call: RoutingCall) {
        call.respond(HttpStatusCode.OK, GET_RESPONSE_TEST)
    }

    override suspend fun post(call: RoutingCall) {
        call.respond(HttpStatusCode.OK, POST_RESPONSE_TEST)
    }
}