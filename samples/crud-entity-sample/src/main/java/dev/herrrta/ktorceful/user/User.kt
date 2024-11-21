package dev.herrrta.ktorceful.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val firstName: String,
    val lastName: String
) {
    val fullName get() = "$firstName $lastName"
}
