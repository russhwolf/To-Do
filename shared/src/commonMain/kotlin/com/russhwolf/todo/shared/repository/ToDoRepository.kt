package com.russhwolf.todo.shared.repository

import com.russhwolf.todo.shared.db.ToDo
import com.russhwolf.todo.shared.db.ToDoDatabase
//import app.cash.sqldelight.runtime.coroutines.asFlow
//import app.cash.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ToDoRepository(
    private val database: ToDoDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    fun getList(): Flow<List<ToDo>> = database.toDoQueries.selectAll().asFlow().mapToList(dispatcher)

    suspend fun add(content: String) = withContext(scope.coroutineContext) {
        database.toDoQueries.insertToDo(content)
    }

    suspend fun remove(toDo: ToDo) = withContext(scope.coroutineContext) {
        database.toDoQueries.deleteById(toDo.id)
    }

    suspend fun toggleComplete(toDo: ToDo) = withContext(scope.coroutineContext) {
        database.toDoQueries.updateComplete(!toDo.complete, toDo.id)
    }
}
