package com.picpay.desafio.android.repository

import com.picpay.desafio.android.api.PicPayService

class BaseRepositoryImpl (
    private val picPayService: PicPayService
): BaseRepository {

    override suspend fun getUsers() =
        picPayService.getUsers()
    }