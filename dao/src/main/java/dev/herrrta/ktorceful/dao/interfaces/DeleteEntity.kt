package dev.herrrta.ktorceful.dao.interfaces

import io.ktor.server.routing.RoutingCall

interface DeleteEntity<E : Any, PK: Any> : EntityRoute {
    suspend fun delete(call: RoutingCall, pk: PK)
    suspend fun hasDeletePermission(call: RoutingCall): Boolean = true
}