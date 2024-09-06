package com.russhwolf.todo.shared

import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.russhwolf.todo.shared.db.ToDoDatabase
import com.russhwolf.todo.shared.repository.ToDoRepository

@Suppress("unused") // Called from Swift
fun createRepository(): ToDoRepository {
    val driver = NativeSqliteDriver(ToDoDatabase.Schema, "ToDoDatabase.db")
    val database = ToDoDatabase(driver)
    return ToDoRepository(database)
}
