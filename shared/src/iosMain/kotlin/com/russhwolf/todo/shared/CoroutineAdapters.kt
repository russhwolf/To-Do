package com.russhwolf.todo.shared

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlin.native.concurrent.freeze

class FlowAdapter<T : Any>(
    private val scope: CoroutineScope,
    private val flow: Flow<T>
) {
    init {
        freeze()
    }

    fun subscribe(
        onEvent: (T) -> Unit,
        onError: (Throwable) -> Unit,
        onComplete: () -> Unit
    ): Job =
        flow
            .onEach { onEvent(it.freeze()) }
            .catch { onError(it.freeze()) }
            .onCompletion { onComplete() }
            .launchIn(scope)
}
