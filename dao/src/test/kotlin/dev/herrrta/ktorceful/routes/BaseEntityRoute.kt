package dev.herrrta.ktorceful.routes

import dev.herrrta.ktorceful.dao.interfaces.CreateEntity
import dev.herrrta.ktorceful.dao.interfaces.DeleteEntity
import dev.herrrta.ktorceful.dao.interfaces.EntityAction
import dev.herrrta.ktorceful.dao.interfaces.GetEntity
import dev.herrrta.ktorceful.dao.interfaces.UpdateEntity
import io.ktor.resources.Resource
import kotlinx.serialization.Serializable

@Resource("api")
class API

@Serializable
abstract class Base<E: Any> (
    val parent: API = API()
): CreateEntity<E>, GetEntity<E>, UpdateEntity<E>, DeleteEntity<E>, EntityAction<E>