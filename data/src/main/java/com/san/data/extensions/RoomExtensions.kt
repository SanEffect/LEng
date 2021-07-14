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


/*
suspend fun <T> doQuery(
    dispatcher: CoroutineDispatcher,
    fn: suspend () -> T
): Either<Failure, T> = withContext(dispatcher) {
    try {
        Either.Success(fn.invoke())
    } catch (e: Exception) {
        Either.Error(Failure.CommonError(e))
    }
}
*/
