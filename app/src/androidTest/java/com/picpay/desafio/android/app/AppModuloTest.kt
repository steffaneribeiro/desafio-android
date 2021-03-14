package com.picpay.desafio.android.app

import com.picpay.desafio.android.api.retrofit
import org.koin.dsl.module


fun networkModuleTest(baseUrl: String) = module {
    single(override = true) { retrofit(get(), baseUrl) }
}
