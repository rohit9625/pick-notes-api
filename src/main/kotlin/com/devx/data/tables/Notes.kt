package com.devx.data.tables

import org.jetbrains.exposed.sql.Table

object Notes: Table() {
    val id = integer("id").autoIncrement()
    val userId = integer("userId").references(Users.id)
    val title = text("title")
    val description = text("description")

    override val primaryKey = PrimaryKey(id)
}