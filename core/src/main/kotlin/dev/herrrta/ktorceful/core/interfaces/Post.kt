package dev.herrrta.ktorceful.core.interfaces

import io.ktor.server.routing.RoutingCall

interface Post: HTTPMethod {
    suspend fun post(call: RoutingCall)
}