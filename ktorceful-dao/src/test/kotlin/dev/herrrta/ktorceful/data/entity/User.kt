package dev.herrrta.ktorceful.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val firstName: String? = null,
    val active: Boolean = true
)
