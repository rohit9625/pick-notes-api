package com.devx.database.repository

import com.devx.data.models.Note
import com.devx.data.models.NoteRequest
import com.devx.data.tables.Notes
import com.devx.database.Database.dbQuery
import com.devx.database.dao.NotesDao
import com.devx.utils.Response
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class NotesRepository: NotesDao {
    override suspend fun createNote(note: NoteRequest, userId: Int) = dbQuery {
        val result = Notes.insert {
            it[this.userId] = userId
            it[title] = note.title
            it[description] = note.description
        }

        if(result.resultedValues == null) {
            Response(
                data = null,
                message = "Note creation failed"
            )
        }

        Response(
            success = true,
            data = rowToNote(result.resultedValues?.get(0)),
            message = "Note created successfully"
        )
    }

    override suspend fun readNote(noteId: Int) = dbQuery {
        val result = Notes.select { Notes.id eq noteId }.singleOrNull()
        if (result == null) {
            Response(
                message = "Note not found"
            )
        } else {
            Response(
                success = true,
                data = rowToNote(result),
                message = "Note retrieved successfully"
            )
        }
    }

    override suspend fun readAllNotes(userId: Int) = dbQuery {
        val notes = Notes.select { Notes.userId eq userId }.map { rowToNote(it) }

        Response(
            success = true,
            data = notes,
            message = "All notes retrieved successfully"
        )
    }

    override suspend fun updateNote(userId: Int, note: Note) = dbQuery {
        val updatedRows = Notes.update({ Notes.id.eq(note.id) and Notes.userId.eq(userId) }) {
            it[title] = note.title
            it[description] = note.description
        }

        if (updatedRows > 0) {
            Response(
                success = true,
                data = rowToNote(Notes.select { Notes.id eq note.id }.single()),
                message = "Note updation successful"
            )
        } else {
            Response(
                message = "Note updation failed"
            )
        }
    }

    override suspend fun deleteNote(noteId: Int) = dbQuery {
        val note = Notes.select { Notes.id eq noteId }.singleOrNull()

        if(note != null) {
            Notes.deleteWhere { id eq noteId }
            Response(
                success = true,
                data = rowToNote(note),
                message = "Note deletion successful"
            )
        }else {
            Response(
                message = "Note not found"
            )
        }
    }

    private fun rowToNote(row: ResultRow?): Note? {
        return if(row == null) {
            null
        } else {
            Note(
                id = row[Notes.id],
                userId = row[Notes.userId],
                title = row[Notes.title],
                description = row[Notes.description]
            )
        }
    }

}