package com.russhwolf.todo.shared

import android.content.Context
import com.russhwolf.todo.shared.db.ToDoDatabase
import com.russhwolf.todo.shared.repository.ToDoRepository
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

fun createRepository(context: Context): ToDoRepository {
    val driver = AndroidSqliteDriver(ToDoDatabase.Schema, context, "ToDoDatabase.db")
    val database = ToDoDatabase(driver)
    return ToDoRepository(database)
}