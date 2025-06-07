package org.lab.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(
    val name: String,
    val job: String
)