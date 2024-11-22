package dev.herrrta.ktorceful.dao.resources

import io.ktor.resources.Resource

class EntityResource {
    @Resource("action/{name}")
    class Action<T>(val parent: T, val name: String)

    @Resource("{pk}")
    class Pk<T, PK>(val parent: T, val pk: PK)
}