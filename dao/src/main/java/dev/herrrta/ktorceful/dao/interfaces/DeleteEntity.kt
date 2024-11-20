package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.core.interfaces.Delete
import io.ktor.server.routing.RoutingCall

interface DeleteEntity<E : Any> : Delete, EntityRoute<E> {
    suspend fun hasDeletePermission(call: RoutingCall): Boolean = true
}