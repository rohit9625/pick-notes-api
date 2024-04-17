package com.devx

import com.devx.database.Database
import com.devx.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    Database.init() // Initialize the database

    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
