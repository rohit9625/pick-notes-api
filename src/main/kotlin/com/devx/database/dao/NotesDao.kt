package com.devx.database.dao

import com.devx.data.models.Note
import com.devx.data.models.NoteRequest
import com.devx.utils.Response

interface NotesDao {
    suspend fun createNote(note: NoteRequest, userId: Int) : Response<Note>

    suspend fun readNote(noteId: Int): Response<Note>

    suspend fun readAllNotes(userId: Int): Response<List<Note?>>

    suspend fun updateNote(userId: Int, note: Note): Response<Note>

    suspend fun deleteNote(noteId: Int): Response<Note>
}