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
        val database = Database.connect(
            url = System.getenv("DATABASE_URL"),
            user = System.getenv("DB_USER"),
            driver = "org.postgresql.Driver",
            password = System.getenv("DB_PASSWD")
        )
        transaction(database) {
            SchemaUtils.create(Users, Notes)
        }
    }


    suspend fun <T> dbQuery(block: suspend ()-> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
