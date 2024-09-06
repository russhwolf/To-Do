package com.russhwolf.todo.shared.test

import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope

expect fun createTestDbDriver(): SqlDriver

expect fun suspendTest(block: suspend CoroutineScope.() -> Unit)
