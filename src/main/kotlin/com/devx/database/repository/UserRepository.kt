package com.devx.database.repository

import com.devx.data.models.LoginRequest
import com.devx.data.models.User
import com.devx.data.models.UserRequest
import com.devx.data.tables.Users
import com.devx.database.Database.dbQuery
import com.devx.database.dao.UserDao
import com.devx.utils.Encrypt
import com.devx.utils.Response
import io.ktor.http.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UserRepository: UserDao {
    override suspend fun registerUser(user: UserRequest) = dbQuery {
        val hashedPassword = Encrypt.hash(user.password)

        val existingUser = findUserByEmail(user.email)
        return@dbQuery if(existingUser != null) {
            Response(
                message = "User already exists"
            )
        } else {
            val result = Users.insert {
                it[username] = user.username
                it[email] = user.email
                it[password] = hashedPassword
            }

            Response(
                success = true,
                data = rowToUser(result.resultedValues?.get(0)),
                message = "User successfully registered"
            )
        }
    }

    override suspend fun loginUser(user: LoginRequest) = dbQuery {
        val existingUser = findUserByEmail(user.email)
        return@dbQuery if(existingUser == null){
            Response(
                data = HttpStatusCode.NotFound,
                message = "User not found"
            )
        } else {
            val isPasswordCorrect = Encrypt.match(user.password, existingUser.password)

            if(isPasswordCorrect) {
                Response(
                    success = true,
                    data = existingUser,
                    message = "User successfully logged in"
                )
            } else {
                Response(
                    data = HttpStatusCode.Unauthorized,
                    message = "Invalid password"
                )
            }
        }
    }

    override suspend fun findUserByEmail(email: String): User? {
        return Users.select { Users.email eq email }
            .map { rowToUser(it) }
            .singleOrNull()
    }

    private fun rowToUser(row: ResultRow?): User? {
        return if(row == null) {
            null
        }else {
            User(
                id = row[Users.id],
                username = row[Users.username],
                email = row[Users.email],
                password = row[Users.password]
            )
        }

    }
}