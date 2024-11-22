package dev.herrrta.ktorceful.dao.interfaces

import dev.herrrta.ktorceful.core.interfaces.Get
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall
import io.ktor.util.reflect.TypeInfo
import kotlin.reflect.full.starProjectedType

interface GetEntity<E : Any, PK: Any> : Get, EntityRoute<E> {
    suspend fun get(call: RoutingCall, pk: PK) {
        val entity = getInstance(pk) ?: return call.respond(HttpStatusCode.BadRequest)

        call.respond(
            entity,
            TypeInfo(klass, klass.starProjectedType)
        )
    }

    suspend fun getInstance(pk: PK): E?
}