package com.devx.data.models

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val username: String,
    val email: String,
    val password: String
)