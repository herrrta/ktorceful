package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.core.interfaces.Post
import io.ktor.server.routing.RoutingCall

interface CreateEntity<E : Any> : Post, EntityRoute<E> {
    suspend fun hasCreatePermission(call: RoutingCall): Boolean = true
}