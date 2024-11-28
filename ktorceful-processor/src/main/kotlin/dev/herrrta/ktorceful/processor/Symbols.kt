package dev.herrrta.ktorceful.processor

import dev.herrrta.ktorceful.dao.resources.EntityResource

internal object Symbols {
    val KTORCEFUL_ENTITY_RESOURCE = EntityResource::class.qualifiedName
    const val KTOR_APPLICATION = "io.ktor.server.application.Application"
    const val KTOR_ROUTING = "io.ktor.server.routing.routing"
    const val KTOR_RESOURCES = "io.ktor.server.resources.*"
    const val KTOR_RESOURCES_RESOURCE = "io.ktor.server.resources.Resource"
}