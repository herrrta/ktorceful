package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.core.interfaces.Delete
import io.ktor.server.routing.RoutingCall

interface DeleteEntity<E : Any, PK: Any> :EntityRoute<E> {
    suspend fun delete(call: RoutingCall, pk: PK)
    suspend fun hasDeletePermission(call: RoutingCall): Boolean = true
}