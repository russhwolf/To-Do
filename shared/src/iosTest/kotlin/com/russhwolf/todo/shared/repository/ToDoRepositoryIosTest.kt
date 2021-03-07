package com.russhwolf.todo.shared.repository

import app.cash.turbine.test
import com.russhwolf.todo.shared.FlowAdapter
import com.russhwolf.todo.shared.db.ToDo
import com.russhwolf.todo.shared.db.ToDoDatabase
import com.russhwolf.todo.shared.test.createTestDbDriver
import com.russhwolf.todo.shared.test.suspendTest
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ToDoRepositoryIosTest {

    private val driver = createTestDbDriver()
    private val repository = ToDoRepositoryIos(ToDoDatabase(driver))

    @AfterTest
    fun resetDriver() {
        driver.close()
    }

    @Test
    fun happyPath() = suspendTest {
        val expectedToDos = ToDos(
            listOf(
                ToDo(id = 1, content = "Make a list", complete = false),
                ToDo(id = 2, content = "Check it twice", complete = false)
            )
        )

        repository.add(expectedToDos.items[0].content).join()
        repository.add(expectedToDos.items[1].content).join()

        repository.getList().flow().test {
            assertEquals(expectedToDos, expectItem())
            expectNoEvents()

            repository.toggleComplete(expectedToDos.items[0]).join()
            assertEquals(
                ToDos(listOf(expectedToDos.items[0].copy(complete = true), expectedToDos.items[1])),
                expectItem()
            )
            expectNoEvents()

            repository.remove(expectedToDos.items[1]).join()
            assertEquals(
                ToDos(listOf(expectedToDos.items[0].copy(complete = true))),
                expectItem()
            )
            expectNoEvents()
        }
    }
}

fun <T : Any> FlowAdapter<T>.flow() = callbackFlow {
    val job = subscribe(
        onEvent = { offer(it) },
        onError = { throw it },
        onComplete = { cancel() }
    )
    awaitClose {
        job.cancel()
    }
}
