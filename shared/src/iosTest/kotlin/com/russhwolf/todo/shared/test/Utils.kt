package com.russhwolf.todo.shared.test

import co.touchlab.sqliter.DatabaseConfiguration
import com.russhwolf.todo.shared.db.ToDoDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.drivers.native.NativeSqliteDriver
import app.cash.sqldelight.drivers.native.wrapConnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking


actual fun createTestDbDriver(): SqlDriver {
    val schema = ToDoDatabase.Schema
    return NativeSqliteDriver(
        DatabaseConfiguration(
            name = "ToDoDatabaseTest.db",
            version = schema.version,
            create = { connection ->
                wrapConnection(connection) { schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) { schema.migrate(it, oldVersion, newVersion) }
            },
            inMemory = true
        )
    )
}

actual fun suspendTest(block: suspend CoroutineScope.() -> Unit) = runBlocking(block = block)
