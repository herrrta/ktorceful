package dev.herrrta.ktorceful.dao.interfaces

import kotlin.reflect.KClass

interface EntityRoute<E : Any> {
    val klass: KClass<E>
}