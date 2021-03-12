package com.picpay.desafio.android.app

import android.app.Application
import com.picpay.desafio.android.api.cache
import com.picpay.desafio.android.api.okHttpClient
import com.picpay.desafio.android.api.retrofit
import com.picpay.desafio.android.api.service
import com.picpay.desafio.android.repository.BaseRepository
import com.picpay.desafio.android.repository.BaseRepositoryImpl
import com.picpay.desafio.android.ui.home.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module


val appModule = module {
    viewModel { HomeViewModel(get()) }
    factory<BaseRepository> { BaseRepositoryImpl(get()) }
}

val networkModule = module {
    single { cache(androidApplication()) }
    single { okHttpClient(get(), androidApplication()) }
    single { service(get()) }
    single { retrofit(get()) }
}

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