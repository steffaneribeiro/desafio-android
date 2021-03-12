package com.picpay.desafio.android.util

import com.picpay.desafio.android.model.User

data class BaseModelState(val data: List<User>?, val status: STATUS, val error: Throwable? = null) {
    enum class STATUS {
        LOADING, SUCCESS, ERROR
    }

    companion object {
        fun success(data: List<User>?) =
            BaseModelState(
                data,
                STATUS.SUCCESS
            )

        fun error(error: Throwable) =
            BaseModelState(
                null, STATUS.ERROR,
                error
            )

        fun loading() =
            BaseModelState(null, STATUS.LOADING)
    }
}