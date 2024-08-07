package com.devx.routes

import com.devx.data.models.LoginRequest
import com.devx.data.models.User
import com.devx.data.models.UserRequest
import com.devx.database.repository.UserRepository
import com.devx.utils.Response
import com.devx.utils.generateToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureUserRoutes() {
    val userRepository = UserRepository()
    post("/register") {
        val user = try {
            call.receive<UserRequest>()
        }catch (e: Exception) {
            println("Error receiving Register Request Object, Error: ${e.message}")
            e.printStackTrace()
            when(e) {
                is CannotTransformContentToTypeException -> return@post call.respond(
                    HttpStatusCode.BadRequest,
                    message = Response(
                        data = null,
                        message = "Request body is required as JSON"
                    )
                )

                is BadRequestException -> return@post call.respond(
                    HttpStatusCode.BadRequest,
                    message = Response(
                        data = null,
                        message = "Some fields are missing"
                    )
                )

                else -> return@post call.respond(
                    HttpStatusCode.InternalServerError,
                    message = Response(
                        data = null,
                        message = "Internal Server Error"
                    )
                )
            }
        }

        if(user.username.isBlank() || user.email.isBlank() || user.password.isBlank()) {
            return@post call.respond(
                status = HttpStatusCode.BadRequest,
                message = Response(
                    data = null,
                    message = "All fields are required"
                )
            )
        }

        try {
            val result = userRepository.registerUser(user)
            if(!result.success) {
                return@post call.respond(
                    status = HttpStatusCode.Conflict,
                    message = result
                )
            }

            val token = generateToken(result.data!!)

            call.respond(
                status = HttpStatusCode.Created,
                message = Response(
                    success = true,
                    data = hashMapOf("token" to token),
                    message = result.message
                )
            )
        }catch (e: Exception) {
            println("Error while registering user: ${e.message}")
            call.respond(
                HttpStatusCode.InternalServerError,
                message = Response(
                    data = null,
                    message = "Internal Server Error"
                )
            )
        }
    }

    post("/login") {
        val user = try {
            call.receive<LoginRequest>()
        }catch (e: Exception) {
            println("Error receiving Login Request Object, Error: ${e.message}")
            when(e) {
                is CannotTransformContentToTypeException -> return@post call.respond(
                    HttpStatusCode.BadRequest,
                    message = Response(
                        data = null,
                        message = "Request body is required in json format"
                    )
                )

                else -> return@post call.respond(
                    HttpStatusCode.InternalServerError,
                    message = Response(
                        data = null,
                        message = "Internal Server Error"
                    )
                )
            }
        }

        if(user.email.isBlank() || user.password.isBlank()) {
            return@post call.respond(
                status = HttpStatusCode.BadRequest,
                message = Response(
                    data = null,
                    message = "All fields are required"
                )
            )
        }

        try {
            val result = userRepository.loginUser(user)
            if(!result.success) {
                return@post call.respond(
                    status = result.data as HttpStatusCode,
                    message = Response(
                        data = null,
                        message = result.message
                    )
                )
            }

            val token = generateToken(result.data!! as User)
            call.respond(
                status = HttpStatusCode.OK,
                message = Response(
                    success = true,
                    data = hashMapOf("token" to token),
                    message = result.message
                )
            )
        }catch (e: Exception) {
            println("Error while logging in user: ${e.message}")
            e.printStackTrace()
            call.respond(
                HttpStatusCode.InternalServerError,
                message = Response(
                    data = null,
                    message = "Internal Server Error"
                )
            )
        }
    }
}