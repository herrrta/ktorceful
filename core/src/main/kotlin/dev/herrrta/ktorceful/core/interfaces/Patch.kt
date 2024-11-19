package dev.herrrta.ktorceful.core.interfaces

import io.ktor.server.routing.RoutingCall

interface Patch: HTTPMethod {
    suspend fun patch(call: RoutingCall)
}