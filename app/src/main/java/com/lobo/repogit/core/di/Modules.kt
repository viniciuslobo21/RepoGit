package com.lobo.repogit.core.di


import com.lobo.repogit.core.RepoGitConstants
import com.lobo.repogit.core.RepoGitConstants.Companion.API_KEY
import com.lobo.repogit.core.helpers.Preferences
import com.lobo.repogit.core.helpers.PreferencesImpl
import com.lobo.repogit.core.helpers.ResourceHelper
import com.lobo.repogit.core.helpers.ResourceHelperImpl
import com.lobo.repogit.data.service.HomeService
import com.lobo.repogit.presentation.home.HomeViewModel
import okhttp3.ConnectionSpec
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit

const val apiKey = "api_key"
const val CONN_TIMEOUT_SEC = 60L

val appModule = module {
    single<Preferences> { PreferencesImpl() }
    single<ResourceHelper> {
        ResourceHelperImpl(
            context = androidContext(),
            preferences = get()
        )
    }
}

val viewModelModule = module {
//    viewModel {
//        HomeViewModel()
//    }
}

val useCaseModule = module {
//    single { HomeUpComingUseCase(homeRepository = get()) }
}

val repositoryModule = module {
//    single<HomeRepository> { HomeRepositoryImpl(service = get()) }
}

val apiModule = module {
    single {
        HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
    }

    single<Interceptor> {
        object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val httpUrl = original.url.newBuilder()
                    .addQueryParameter(apiKey, API_KEY).build()
                val request = original.newBuilder().url(httpUrl).build()
                return chain.proceed(request)
            }
        }
    }

    single {
        val spec = ConnectionSpec
            .Builder(ConnectionSpec.COMPATIBLE_TLS)
            .allEnabledCipherSuites()
            .build()

        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(get<Interceptor>())
            .connectionSpecs(Collections.singletonList(spec))
            .connectTimeout(CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
            .writeTimeout(CONN_TIMEOUT_SEC, TimeUnit.SECONDS)
            .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(RepoGitConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single { get<Retrofit>().create(HomeService::class.java) }
}

