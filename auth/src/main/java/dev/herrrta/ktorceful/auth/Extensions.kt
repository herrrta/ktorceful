package dev.herrrta.ktorceful.auth

import dev.herrrta.ktorceful.core.extensions.setBasicRoutes
import dev.herrrta.ktorceful.core.interfaces.HTTPMethod
import io.ktor.server.application.Application
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.routing

inline fun <reified Route : HTTPMethod> Application.createBasicRoute(
    configurations: Array<String?> = arrayOf(null),
    authOptional: Boolean = false
) {
    routing {
        authenticate(*configurations, optional = authOptional) {
            setBasicRoutes<Route>().invoke(this)
        }
    }
}