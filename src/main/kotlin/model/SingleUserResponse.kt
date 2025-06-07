package org.lab.model

import kotlinx.serialization.Serializable

@Serializable
data class SingleUserResponse(
    val data: User,
    val support: Support
)