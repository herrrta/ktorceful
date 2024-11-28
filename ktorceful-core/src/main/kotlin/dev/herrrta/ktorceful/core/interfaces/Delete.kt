package dev.herrrta.ktorceful.core.interfaces

import io.ktor.server.routing.RoutingCall

interface Delete: HTTPMethod {
    suspend fun delete(call: RoutingCall)
}