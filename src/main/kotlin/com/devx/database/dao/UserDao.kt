package com.devx.database.dao

import com.devx.data.models.LoginRequest
import com.devx.data.models.User
import com.devx.data.models.UserRequest
import com.devx.utils.Response

interface UserDao {
    suspend fun registerUser(user: UserRequest): Any

    suspend fun loginUser(user: LoginRequest): Any

    suspend fun findUserByEmail(email: String): User?
}