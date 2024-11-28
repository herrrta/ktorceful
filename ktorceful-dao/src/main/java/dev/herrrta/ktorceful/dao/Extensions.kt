package dev.herrrta.ktorceful.dao

import dev.herrrta.ktorceful.core.extensions.setBasicRoutes
import dev.herrrta.ktorceful.core.interfaces.HTTPMethod
import dev.herrrta.ktorceful.dao.interfaces.CreateEntity
import dev.herrrta.ktorceful.dao.interfaces.DeleteEntity
import dev.herrrta.ktorceful.dao.interfaces.EntityAction
import dev.herrrta.ktorceful.dao.interfaces.EntityRoute
import dev.herrrta.ktorceful.dao.interfaces.GetEntity
import dev.herrrta.ktorceful.dao.interfaces.UpdateEntity
import dev.herrrta.ktorceful.dao.interfaces.invokeAction
import dev.herrrta.ktorceful.dao.resources.EntityResource
import io.ktor.server.application.Application
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.routing.Route
import io.ktor.server.routing.routing
import kotlin.reflect.full.isSubclassOf

@Suppress("UNCHECKED_CAST")
inline fun <reified ERoute : EntityRoute, reified Entity : Any, reified PK : Any> setEntityRoutes(): Route.() -> Unit = {
    if (ERoute::class.isSubclassOf(CreateEntity::class)) {
        post<ERoute> {
            (it as CreateEntity<Entity>).post(call, Entity::class)
        }
    }
    if (ERoute::class.isSubclassOf(GetEntity::class)) {
        get<EntityResource.Pk<ERoute, PK>> {
            (it.parent as GetEntity<Entity, PK>).get(call, it.pk, Entity::class)
        }
    }
    if (ERoute::class.isSubclassOf(UpdateEntity::class)) {
        put<EntityResource.Pk<ERoute, PK>> {
            (it.parent as UpdateEntity<Entity, PK>).put(call, it.pk, Entity::class)
        }
    }
    if (ERoute::class.isSubclassOf(DeleteEntity::class)) {
        delete<EntityResource.Pk<ERoute, PK>> {
            (it.parent as DeleteEntity<Entity, PK>).delete(call, it.pk)
        }
    }
    if (ERoute::class.isSubclassOf(EntityAction::class)) {
        post<EntityResource.Action<ERoute>> {
            (it.parent as EntityAction<Entity>).invokeAction(call, it.name)
        }
    }
}

inline fun <reified Route, reified Entity : Any, reified PK : Any> Application.createRoutes()
where Route: EntityRoute, Route : HTTPMethod {
    routing {
        setBasicRoutes<Route>().invoke(this)
        setEntityRoutes<Route, Entity, PK>().invoke(this)
    }
}