package com.lobo.repogit.data

import com.lobo.repogit.data.service.HomeService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

interface APIClient {
    fun getAPIService(): HomeService
    fun getRetrofit(): Retrofit
    fun getOkHttpClient(): OkHttpClient
    fun getInterceptor(): Interceptor
}