package com.picpay.desafio.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.repository.BaseRepository
import com.picpay.desafio.android.util.BaseModelState

class HomeViewModel : ViewModel() {

    private val repository = BaseRepository()

    private val _users = MutableLiveData<BaseModelState>()
    val user: LiveData<BaseModelState> = _users

    init {
        getUsers()
    }

    private fun getUsers() {
        _users.value = BaseModelState.loading()

        try {
            repository.getUserList({
                _users.value = BaseModelState.success(it)
            }, {})


        } catch (e: Exception) {
            _users.value = BaseModelState.error(e)
        }

    }


}