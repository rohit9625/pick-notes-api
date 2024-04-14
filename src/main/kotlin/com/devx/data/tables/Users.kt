package com.devx.data.tables

import org.jetbrains.exposed.sql.Table

object Users: Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 28)
    val email = varchar("email", 56)
    val password = varchar("password", 256)
    
    override val primaryKey = PrimaryKey(id)
}