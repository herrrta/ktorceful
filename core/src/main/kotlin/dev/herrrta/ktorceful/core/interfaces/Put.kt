package dev.herrrta.ktorceful.core.interfaces

import io.ktor.server.routing.RoutingCall

interface Put: HTTPMethod {
    suspend fun put(call: RoutingCall)
}