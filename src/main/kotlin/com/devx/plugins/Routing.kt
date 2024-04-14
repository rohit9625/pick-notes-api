package com.devx.plugins

import com.devx.routes.configureNoteRoutes
import com.devx.routes.configureUserRoutes
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("/users") {
            configureUserRoutes()
        }

        route("/notes") {
            configureNoteRoutes()
        }

        get("/") {
            call.respondText("This is an API for pick notes mobile application")
        }
    }
}
