package com.lobo.repogit.data

sealed class Response<out T> {
    data class Success<out T>(val value: T) : Response<T>()
    data class GenericError(val throwable: Throwable) : Response<Nothing>()
}

inline fun <T, A> Response<T>.fold(e: (Throwable) -> A, v: (T) -> A): A = when (this) {
    is Response.GenericError -> e(this.throwable)
    is Response.Success -> v(this.value)
}

inline infix fun <V, V2> Response<V>.flatMap(f: (V) -> Response<V2>): Response<V2> = when (this) {
    is Response.GenericError -> this
    is Response.Success -> f(value)
}

inline infix fun <V, V2> Response<V>.map(f: (V) -> V2): Response<V2> = when (this) {
    is Response.GenericError -> this
    is Response.Success -> Response.Success(f(this.value))
}