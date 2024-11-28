package dev.herrrta.ktorceful.core.interfaces

import io.ktor.server.routing.RoutingCall

interface Options: HTTPMethod {
    suspend fun options(call: RoutingCall)
}