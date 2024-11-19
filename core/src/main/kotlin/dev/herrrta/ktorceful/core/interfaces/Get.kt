package dev.herrrta.ktorceful.core.interfaces

import io.ktor.server.routing.RoutingCall

interface Get: HTTPMethod {
    suspend fun get(call: RoutingCall)
}