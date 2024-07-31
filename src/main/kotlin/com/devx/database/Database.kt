package com.devx.database

import com.devx.data.tables.Notes
import com.devx.data.tables.Users
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object Database {
    fun init() {
        val urlDecoderRegex = Regex("postgres://(.*):(.*)@(.*):(\\d+)/(.*)")
        val dbUrl = System.getenv("DATABASE_URL")
        val matchResult = urlDecoderRegex.find(dbUrl)

        matchResult?.let {
            val (user, password, host, port, db) = matchResult.destructured

            val database = Database.connect(
                url = "jdbc:postgresql://$host:$port/$db",
                user = user,
                driver = "org.postgresql.Driver",
                password = password
            )
            transaction(database) {
                SchemaUtils.create(Users, Notes)
            }
        }?: throw IllegalArgumentException("Environment variable DATABASE_URL is not set")
    }

    suspend fun <T> dbQuery(block: suspend ()-> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
