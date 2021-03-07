package com.russhwolf.todo.shared

import com.russhwolf.todo.shared.db.ToDoDatabase
import com.russhwolf.todo.shared.repository.ToDoRepositoryIos
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

fun createRespository(): ToDoRepositoryIos {
    val driver = NativeSqliteDriver(ToDoDatabase.Schema, "ToDoDatabase.db")
    val database = ToDoDatabase(driver)
    return ToDoRepositoryIos(database)
}