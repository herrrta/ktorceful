package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.core.interfaces.Put
import io.ktor.server.routing.RoutingCall

interface UpdateEntity<E : Any, PK : Any> : EntityRoute<E> {
    suspend fun put(call: RoutingCall, pk: PK)
    suspend fun hasUpdatePermission(call: RoutingCall): Boolean = true
}