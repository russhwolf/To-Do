package com.russhwolf.todo.androidApp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.russhwolf.todo.shared.db.ToDo

@Composable
fun ToDoList(
    toDos: List<ToDo>,
    onCreateItem: (String) -> Unit,
    onCheckItem: (ToDo) -> Unit,
    onRemoveItem: (ToDo) -> Unit
) = ToDoTheme {
    Surface {
        Column(Modifier.fillMaxSize()) {
            ToDoInput(onCreateItem = onCreateItem)
            LazyColumn {
                items(toDos.size) { index ->
                    val toDo = toDos[index]
                    ToDoItem(
                        content = toDo.content,
                        checked = toDo.complete,
                        onCheckedClick = { onCheckItem(toDo) },
                        onDeleteClick = { onRemoveItem(toDo) }
                    )
                }
            }
        }
    }
}

@Composable
fun ToDoInput(
    onCreateItem: (String) -> Unit
) = Column {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val input = remember { mutableStateOf(TextFieldValue()) }

        fun createToDo() {
            if (input.value.text.isBlank()) return
            onCreateItem(input.value.text)
            input.value = input.value.copy(text = "")
        }

        OutlinedTextField(
            value = input.value,
            onValueChange = { input.value = it },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { createToDo() }),
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
        )
        OutlinedButton(
            onClick = { createToDo() },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Create")
        }
    }
    Divider()
}

@Composable
fun ToDoItem(
    content: String,
    checked: Boolean,
    onCheckedClick: () -> Unit,
    onDeleteClick: () -> Unit
) = Column {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedClick() },
            modifier = Modifier.padding(8.dp)
        )
        Text(content, Modifier.weight(1f))
        IconButton(
            onClick = { onDeleteClick() }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = MaterialTheme.colors.error
            )
        }
    }
    Divider()
}

@Composable
fun ToDoTheme(content: @Composable () -> Unit) = MaterialTheme(
    colors = lightColors(
        primary = Color(0xFF3759DF),
        secondary = Color(0xFF375A86)
    ),
    typography = MaterialTheme.typography,
    shapes = MaterialTheme.shapes,
    content = content
)

@Preview
@Composable
fun ToDoListPreview() {
    val toDos = remember {
        mutableStateOf(
            listOf(
                ToDo(0, "Make a list", true),
                ToDo(1, "Check it twice", false),
            )
        )
    }
    ToDoList(
        toDos = toDos.value,
        onCreateItem = {
            toDos.value += ToDo(toDos.value.lastOrNull()?.id?.plus(1) ?: 0, it, false)
        },
        onCheckItem = {
            toDos.value = toDos.value.toMutableList().apply {
                set(toDos.value.indexOf(it), it.copy(complete = !it.complete))
            }.toList()
        },
        onRemoveItem = {
            toDos.value -= it
        }
    )
}
