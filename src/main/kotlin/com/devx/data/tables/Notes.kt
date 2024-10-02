package com.devx.data.tables

import org.jetbrains.exposed.sql.Table

object Notes: Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("userId").references(Users.id)
    val title = text("title")
    val description = text("description")
    val createdAt = long("createdAt")
    val updatedAt = long("updatedAt")

    override val primaryKey = PrimaryKey(id)
}