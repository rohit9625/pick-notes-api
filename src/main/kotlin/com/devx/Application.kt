package com.devx

import com.devx.database.Database
import com.devx.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Database.init() // Initialize the database

    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
