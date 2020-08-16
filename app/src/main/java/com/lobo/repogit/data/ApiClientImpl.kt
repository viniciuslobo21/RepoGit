package com.lobo.repogit.data

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.lobo.repogit.core.RepoGitConstants
import com.lobo.repogit.core.di.CONN_TIMEOUT_SEC
import com.lobo.repogit.core.di.apiKey
import com.lobo.repogit.data.service.HomeService
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

class ApiClientImpl : APIClient {
    override fun getAPIService(): HomeService {
        return getRetrofit().create(HomeService::class.java)
    }

    override fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(RepoGitConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(getOkHttpClient())
            .build()
    }

    override fun getOkHttpClient(): OkHttpClient {
        val spec = ConnectionSpec
            .Builder(ConnectionSpec.COMPATIBLE_TLS)
            .allEnabledCipherSuites()
            .build()


        return OkHttpClient.Builder()
            .addInterceptor(getInterceptor())
            .addInterceptor(getInterceptor())
            .connectionSpecs(Collections.singletonList(spec))
            .connectTimeout(CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
            .build()
    }

    override fun getInterceptor(): Interceptor {
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val httpUrl = original.url.newBuilder()
                    .addQueryParameter(apiKey, RepoGitConstants.API_KEY).build()
                val request = original.newBuilder().url(httpUrl).build()
                return chain.proceed(request)
            }
        }
    }
}