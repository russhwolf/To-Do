package com.russhwolf.todo.shared.test

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import com.russhwolf.todo.shared.db.ToDoDatabase

actual fun createTestDbDriver(): SqlDriver {
    val schema = ToDoDatabase.Schema
    return inMemoryDriver(schema)
}
