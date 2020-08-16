package com.lobo.repogit.core

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.lobo.repogit.core.di.*
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class RepoGitApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        startKoin {
            androidContext(this@RepoGitApplication)
            androidLogger()
            modules(
                listOf(
                    appModule,
                    viewModelModule,
                    apiModule,
                    useCaseModule,
                    repositoryModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}