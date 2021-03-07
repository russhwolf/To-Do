package com.russhwolf.todo.shared.test

import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope

expect fun createTestDbDriver(): SqlDriver

expect fun suspendTest(block: suspend CoroutineScope.() -> Unit)
