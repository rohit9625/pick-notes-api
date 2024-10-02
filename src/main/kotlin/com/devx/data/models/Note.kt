package com.devx.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val id: Int = 0,
    val userId: Int,
    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long
)