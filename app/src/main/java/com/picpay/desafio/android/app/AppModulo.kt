package com.picpay.desafio.android.app

import android.app.Application
import com.picpay.desafio.android.di.appModule
import com.picpay.desafio.android.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    networkModule,
                    appModule
                )
            )
        }
    }
}