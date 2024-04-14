package com.devx.routes

import com.devx.data.models.Note
import com.devx.data.models.NoteRequest
import com.devx.database.Database.dbQuery
import com.devx.database.repository.NotesRepository
import com.devx.database.repository.UserRepository
import com.devx.utils.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureNoteRoutes() {
    val userRepository = UserRepository()
    val noteRepository = NotesRepository()

    authenticate("auth-jwt") {
        post("/create") {
            val note = try {
                call.receive<NoteRequest>()
            }catch (e: Exception) {
                println("Error receiving Note Request Object, Error: ${e.message}")
                return@post call.respond(
                    HttpStatusCode.BadRequest,
                    message = Response(
                        data = null,
                        message = "Please provide required fields"
                    )
                )
            }

            val email = call.principal<JWTPrincipal>()?.payload?.getClaim("email")?.asString()
            val user = dbQuery {
                userRepository.findUserByEmail(email!!)
            }

            try {
                val result = noteRepository.createNote(note, user?.id!!)
                call.respond(
                    status = HttpStatusCode.Created,
                    message = result
                )

            }catch (e: Exception) {
                println("Error while creating a new note, Error: ${e.message}")
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = Response(
                        data = null,
                        message = "Internal Server Error"
                    )
                )
            }
        }

        get("/{id?}") {
            val noteId = call.parameters["id"]

            try {
                val result = noteRepository.readNote(noteId?.toInt()!!)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = result
                )

            }catch (e: Exception) {
                println("Error while reading to a note, Error: ${e.message}")
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = Response(
                        data = null,
                        message = "Internal Server Error"
                    )
                )
            }
        }

        get {
            val email = call.principal<JWTPrincipal>()?.payload?.getClaim("email")?.asString()
            val user = dbQuery {
                userRepository.findUserByEmail(email!!)
            }

            try {
                val result = noteRepository.readAllNotes(user?.id!!)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = result
                )

            }catch (e: Exception) {
                println("Error while creating a new note, Error: ${e.message}")
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = Response(
                        data = null,
                        message = "Internal Server Error"
                    )
                )
            }
        }

        put("/update") {
            val note = try {
                call.receive<Note>()
            }catch (e: Exception) {
                println("Error receiving Note Request Object, Error: ${e.message}")
                return@put call.respond(
                    HttpStatusCode.BadRequest,
                    message = Response(
                        data = null,
                        message = "Please provide required fields"
                    )
                )
            }

            val email = call.principal<JWTPrincipal>()?.payload?.getClaim("email")?.asString()
            val user = dbQuery {
                userRepository.findUserByEmail(email!!)
            }

            try {
                val result = noteRepository.updateNote(user?.id!!, note)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = result
                )

            }catch (e: Exception) {
                println("Error while creating a new note, Error: ${e.message}")
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = Response(
                        data = null,
                        message = "Internal Server Error"
                    )
                )
            }
        }

        delete("/delete/{id?}") {
            val noteId = call.parameters["id"]

            try {
                val result = noteRepository.deleteNote(noteId?.toInt()!!)
                call.respond(
                    status = HttpStatusCode.OK,
                    message = result
                )

            }catch (e: Exception) {
                println("Error while creating a new note, Error: ${e.message}")
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    message = Response(
                        data = null,
                        message = "Internal Server Error"
                    )
                )
            }
        }
    }
}