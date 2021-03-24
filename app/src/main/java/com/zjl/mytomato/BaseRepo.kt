package com.zjl.mytomato

import kotlinx.coroutines.*

open class BaseRepo(
    private val coroutineScope: CoroutineScope
) {

    protected fun <T> launch(
        block: suspend () -> T,
        success: suspend (T) -> Unit = {},
        fail: suspend (Throwable) -> Unit = {}
    ): Job {
        return coroutineScope.launch {
            kotlin.runCatching {
                withContext(Dispatchers.IO) {
                    block.invoke()
                }
            }.onSuccess {
                success.invoke(it)
            }.onFailure {
                fail.invoke(it)
            }
        }
    }
}