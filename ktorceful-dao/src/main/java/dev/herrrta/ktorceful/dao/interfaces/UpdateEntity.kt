package dev.herrrta.ktorceful.dao.interfaces

import io.ktor.server.routing.RoutingCall
import kotlin.reflect.KClass

interface UpdateEntity<E : Any, PK : Any> : EntityRoute {
    suspend fun put(call: RoutingCall, pk: PK, klass: KClass<E>)
    suspend fun hasUpdatePermission(call: RoutingCall): Boolean = true
}