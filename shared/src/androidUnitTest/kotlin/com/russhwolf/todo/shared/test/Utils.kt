package com.russhwolf.todo.shared.test

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.russhwolf.todo.shared.db.ToDoDatabase

actual fun createTestDbDriver(): SqlDriver =
    JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        .also { ToDoDatabase.Schema.create(it) }
