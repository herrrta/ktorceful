package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.core.interfaces.Put
import io.ktor.server.routing.RoutingCall

interface UpdateEntity<E : Any> : Put, EntityRoute<E> {
    suspend fun hasUpdatePermission(call: RoutingCall): Boolean = true
}