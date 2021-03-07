package com.russhwolf.todo.shared.test

import com.russhwolf.todo.shared.db.ToDoDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun createTestDbDriver(): SqlDriver =
    JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        .also { ToDoDatabase.Schema.create(it) }

actual fun suspendTest(block: suspend CoroutineScope.() -> Unit) = runBlocking(block = block)
