package com.russhwolf.todo.shared.repository

import com.russhwolf.todo.shared.FlowAdapter
import com.russhwolf.todo.shared.db.ToDo
import com.russhwolf.todo.shared.db.ToDoDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.native.concurrent.freeze

class ToDoRepositoryIos(database: ToDoDatabase, dispatcher: CoroutineDispatcher = Dispatchers.Default) {
    private val repository = ToDoRepository(database, dispatcher)
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    init {
        freeze()
    }

    fun getList() = FlowAdapter(scope, repository.getList().map(::ToDos))
    fun add(content: String) = scope.launch { repository.add(content) }
    fun remove(toDo: ToDo) = scope.launch { repository.remove(toDo) }
    fun toggleComplete(toDo: ToDo) = scope.launch { repository.toggleComplete(toDo) }
}

// This lets us do Flow<ToDos> instead of Flow<List<ToDo>> for better iOS generics experience
data class ToDos(val items: List<ToDo>) {
    init {
        freeze()
    }
}
