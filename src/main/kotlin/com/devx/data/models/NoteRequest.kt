package com.devx.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val id: Int? = null,
    val title: String,
    val description: String
)