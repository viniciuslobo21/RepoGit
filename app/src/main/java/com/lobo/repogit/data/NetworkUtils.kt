package com.lobo.repogit.data

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection

@ExperimentalCoroutinesApi
fun <T> Deferred<Response<T>>.safeAwait(
    default: T? = null
): Flow<T> = flow {
    val response = this@safeAwait.await()

    when (response.isSuccessful) {
        true -> emit((response.body() ?: (default ?: throw NullPointerException())))
        false -> handleError(response)
    }
}

fun <T> handleError(response: Response<T>) {
    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
        throw HttpException(response)
    }
}


