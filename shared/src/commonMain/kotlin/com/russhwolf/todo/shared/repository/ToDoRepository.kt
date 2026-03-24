package com.russhwolf.todo.shared.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.russhwolf.todo.shared.db.ToDo
import com.russhwolf.todo.shared.db.ToDoDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ToDoRepository(
    private val database: ToDoDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    fun getList(): Flow<List<ToDo>> = database.toDoQueries.selectAll().asFlow().mapToList(dispatcher)

    suspend fun add(content: String) = withContext(dispatcher) {
        database.toDoQueries.insertToDo(content)
    }

    suspend fun remove(toDo: ToDo) = withContext(dispatcher) {
        database.toDoQueries.deleteById(toDo.id)
    }

    suspend fun toggleComplete(toDo: ToDo) = withContext(dispatcher) {
        database.toDoQueries.updateComplete(!toDo.complete, toDo.id)
    }
}
