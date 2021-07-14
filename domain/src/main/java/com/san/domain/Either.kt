package com.san.domain

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {

    /** * Represents the left side of [Either] class which by convention is a "Error". */
    data class Error<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Success<out R>(val b: R) : Either<Nothing, R>()

    /**
     * Returns true if this is a Success, false otherwise.
     * @see Right
     */
    val isSuccess get() = this is Success<R>

    /**
     * Returns true if this is a Error, false otherwise.
     * @see Left
     */
    val isError get() = this is Error<L>

    /**
     * Creates a Error type.
     * @see Left
     */
    fun <L> error(a: L) = Either.Error(a)


    /**
     * Creates a Error type.
     * @see Right
     */
    fun <R> success(b: R) = Either.Success(b)

    /**
     * Applies fnL if this is a Error or fnR if this is a Success.
     * @see Left
     * @see Right
     */
    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Error -> fnL(a)
            is Success -> fnR(b)
        }
}

/**
 * Composes 2 functions
 * See <a href="https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">Credits to Alex Hart.</a>
 */
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Right-biased flatMap() FP convention which means that Success is assumed to be the default case
 * to operate on. If it is Error, operations like map, flatMap, ... return the Error value unchanged.
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Error -> Either.Error(a)
        is Either.Success -> fn(b)
    }

/**
 * Right-biased map() FP convention which means that Success is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::success))

/** Returns the value from this `Right` or the given argument if this is a `Left`.
 *  Right(12).getOrElse(17) RETURNS 12 and Left(12).getOrElse(17) RETURNS 17
 */
fun <L, R> Either<L, R>.getOrElse(value: R): R =
    when (this) {
        is Either.Error -> value
        is Either.Success -> b
    }
