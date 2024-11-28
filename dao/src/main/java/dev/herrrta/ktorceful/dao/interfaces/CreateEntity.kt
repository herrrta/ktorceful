package dev.herrrta.ktorceful.dao.interfaces

import io.ktor.server.routing.RoutingCall
import kotlin.reflect.KClass

interface CreateEntity<E : Any> : EntityRoute {
    suspend fun post(call: RoutingCall, klass: KClass<E>)
    suspend fun hasCreatePermission(call: RoutingCall): Boolean = true
}