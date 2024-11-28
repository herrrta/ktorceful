package dev.herrrta.ktorceful.core.extensions

import dev.herrrta.ktorceful.core.interfaces.Delete
import dev.herrrta.ktorceful.core.interfaces.Get
import dev.herrrta.ktorceful.core.interfaces.HTTPMethod
import dev.herrrta.ktorceful.core.interfaces.Options
import dev.herrrta.ktorceful.core.interfaces.Patch
import dev.herrrta.ktorceful.core.interfaces.Post
import dev.herrrta.ktorceful.core.interfaces.Put
import io.ktor.server.application.Application
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.options
import io.ktor.server.resources.patch
import io.ktor.server.resources.post
import io.ktor.server.resources.put
import io.ktor.server.routing.Route
import io.ktor.server.routing.routing
import kotlin.reflect.full.isSubclassOf

inline fun <reified Method : HTTPMethod> setBasicRoutes(): Route.() -> Unit = {
    if (Method::class.isSubclassOf(Get::class)) {
        get<Method> { (it as Get).get(call) }
    }
    if (Method::class.isSubclassOf(Post::class)) {
        post<Method> { (it as Post).post(call) }
    }
    if (Method::class.isSubclassOf(Delete::class)) {
        delete<Method> { (it as Delete).delete(call) }
    }
    if (Method::class.isSubclassOf(Put::class)) {
        put<Method> { (it as Put).put(call) }
    }
    if (Method::class.isSubclassOf(Patch::class)) {
        patch<Method> { (it as Patch).patch(call) }
    }
    if (Method::class.isSubclassOf(Options::class)) {
        options<Method> { (it as Options).options(call) }
    }
}

inline fun <reified Method : HTTPMethod> Application.createRoutes() {
    routing {
        setBasicRoutes<Method>().invoke(this)
    }
}