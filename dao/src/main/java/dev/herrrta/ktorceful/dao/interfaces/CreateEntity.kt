package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.core.interfaces.Get
import io.ktor.server.routing.RoutingCall

interface CreateEntity<E : Any> : Get, EntityRoute<E> {
    suspend fun hasCreatePermission(call: RoutingCall): Boolean = true
}