package com.devx.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {
    // Please read the jwt property from the config file if you are using EngineMain
    val jwtRealm = "User Authentication"
    val jwtAudience = "http://127.0.0.1:8080/users"
    val jwtIssuer = "http://127.0.0.1:8080/"
    val jwtSecret = System.getenv("JWT_SECRET")

    authentication {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                val email = credential.payload.getClaim("email").asString()
                if(email.isNotBlank())
                    JWTPrincipal(credential.payload)
                else
                    null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
