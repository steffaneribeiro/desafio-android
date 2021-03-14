package com.picpay.desafio.android.di

import com.picpay.desafio.android.api.cache
import com.picpay.desafio.android.api.okHttpClient
import com.picpay.desafio.android.api.retrofit
import com.picpay.desafio.android.api.service
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


val networkModule = module {
    single { cache(androidApplication()) }
    single { okHttpClient(get(), androidApplication()) }
    single { service(get()) }
    single { retrofit(get()) }
}

