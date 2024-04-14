package com.devx.data.models

import kotlinx.serialization.Serializable

@Serializable
data class NoteRequest(
    val title: String,
    val description: String
)