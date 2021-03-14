package com.picpay.desafio.android.di

import com.picpay.desafio.android.repository.BaseRepository
import com.picpay.desafio.android.repository.BaseRepositoryImpl
import com.picpay.desafio.android.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get()) }
    factory<BaseRepository> { BaseRepositoryImpl(get()) }
}