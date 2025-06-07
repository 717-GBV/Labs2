package org.lab.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(
    val name: String?,
    val job: String?,
    val id: String,
    val createdAt: String
)