package com.devx.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.devx.data.models.User

const val jwtAudience = "http://127.0.0.1:8080/users"
const val jwtIssuer = "http://127.0.0.1:8080/"
val jwtSecret: String = System.getenv("JWT_SECRET")

fun generateToken(user: User): String {
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim("email", user.email)
        .sign(Algorithm.HMAC256(jwtSecret))
}