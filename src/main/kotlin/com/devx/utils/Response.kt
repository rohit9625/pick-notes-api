package com.devx.utils

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val success: Boolean = false,
    val data: T? = null,
    val message: String? = null
)