package com.san.data

import com.san.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.withContext

suspend fun <T> doRequest(
    dispatcher: CoroutineDispatcher,
    func: suspend () -> Deferred<T>
): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.Success(func.invoke().await())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}
