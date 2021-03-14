package com.picpay.desafio.android.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.repository.BaseRepository
import com.picpay.desafio.android.util.BaseModelState
import kotlinx.coroutines.launch

class HomeViewModel(private val useCase: BaseRepository) : ViewModel() {

    private val _users = MutableLiveData<BaseModelState>()
    val user: LiveData<BaseModelState> = _users

    init {
        getUsers()
    }

    private fun getUsers() {

        _users.value = BaseModelState.loading()
        viewModelScope.launch {

            try {
                _users.value = BaseModelState.success(useCase.getUsers())

            } catch (e: Exception) {
                _users.value = BaseModelState.error(e)
            }
        }
    }
}