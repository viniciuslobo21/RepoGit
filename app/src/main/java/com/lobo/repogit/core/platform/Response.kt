package com.lobo.repogit.core.platform

sealed class Response<out T> {
    data class Success<out T>(val value: T) : Response<T>()
    data class Failure(val error: Throwable) : Response<Nothing>()

    fun <V> success(value: V): Response<V> = Success(value)
    fun failure(value: Throwable): Response<Nothing> = Failure(value)
}

inline infix fun <V, V2> Response<V>.flatMap(f: (V) -> Response<V2>): Response<V2> = when (this) {
    is Response.Failure -> this
    is Response.Success -> f(value)
}

inline infix fun <V, V2> Response<V>.map(f: (V) -> V2): Response<V2> = when (this) {
    is Response.Failure -> this
    is Response.Success -> Response.Success(f(this.value))
}

infix fun <V, V2> Response<(V) -> V2>.apply(f: Response<V>): Response<V2> = when (this) {
    is Response.Failure -> this
    is Response.Success -> f.map(this.value)
}

inline fun <T, A> Response<T>.fold(e: (Throwable) -> A, v: (T) -> A): A = when (this) {
    is Response.Failure -> e(this.error)
    is Response.Success -> v(this.value)
}