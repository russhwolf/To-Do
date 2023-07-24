package com.russhwolf.todo.shared.test

import com.russhwolf.todo.shared.db.ToDoDatabase
import app.cash.sqldelight.sqlite.driver.JdbcSqliteDriver
import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun createTestDbDriver(): SqlDriver =
    JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        .also { ToDoDatabase.Schema.create(it) }

actual fun suspendTest(block: suspend CoroutineScope.() -> Unit) = runBlocking(block = block)
