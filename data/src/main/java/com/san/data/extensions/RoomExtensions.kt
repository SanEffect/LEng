package com.san.data.extensions

import com.san.domain.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> doQuery(
    dispatcher: CoroutineDispatcher,
    fn: suspend () -> T
): Result<T> = withContext(dispatcher) {
    try {
        Result.Success(fn.invoke())
    } catch (e: Exception) {
        Result.Error(e)
    }
}

fun <Type> performIfSuccess(result: Result<Type>, perform: () -> Unit): Result<Type> {
    if(result is Result.Success) {
        perform()
    }
    return result
}
