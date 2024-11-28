package dev.herrrta.ktorceful

import dev.herrrta.ktorceful.core.interfaces.Get
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall

@Resource("test-auth")
class TestAuthRoute: Get {
    companion object {
        const val GET_RESPONSE_TEST = "GET Test response"
    }

    override suspend fun get(call: RoutingCall) {
        call.respond(HttpStatusCode.OK, GET_RESPONSE_TEST)
    }
}