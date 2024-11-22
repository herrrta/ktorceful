package dev.herrrta.sample

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long = -1,
    val firstName: String = "",
    val lastName: String = "",
    val isActive: Boolean = true
) {
    val fullName get() = "$firstName $lastName"
}