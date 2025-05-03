package dev.herrrta.ktorceful.dao.interfaces

import io.ktor.server.routing.RoutingCall
import kotlin.reflect.KClass

interface DeleteEntity<E : Any, PK: Any> : EntityRoute {
    suspend fun delete(call: RoutingCall, pk: PK, klass: KClass<E>)
    suspend fun hasDeletePermission(call: RoutingCall): Boolean = true
}