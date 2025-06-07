package org.lab.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserResponse(
    val name: String?,
    val job: String?,
    val updatedAt: String
)