package com.picpay.desafio.android.repository

import com.picpay.desafio.android.api.PicPayApi
import com.picpay.desafio.android.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BaseRepository {
    private val service = PicPayApi().providePicPayService()

    fun getUserList(
        success: (List<User>?) -> Unit,
        fail: (String?) -> Unit
    ) {
        CoroutineScope(IO).launch {
            val users = service.getUsers()
            withContext(Dispatchers.Main) {
                if (users.isSuccessful) {
                    success(users.body())
                } else {
                    fail(users.message())
                }
            }
        }
    }
}