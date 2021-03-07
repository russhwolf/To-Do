package com.russhwolf.todo.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.russhwolf.todo.shared.createRepository
import kotlinx.coroutines.launch

class ToDoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = createRepository(applicationContext)

        setContent {
            val toDos = repository.getList().collectAsState(initial = emptyList())

            ToDoList(
                toDos = toDos.value,
                onCreateItem = {
                    lifecycleScope.launch { repository.add(it) }
                },
                onCheckItem = {
                    lifecycleScope.launch { repository.toggleComplete(it) }
                },
                onRemoveItem = {
                    lifecycleScope.launch { repository.remove(it) }
                }
            )
        }
    }
}

