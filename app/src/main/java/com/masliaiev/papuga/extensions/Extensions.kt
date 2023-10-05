package com.masliaiev.papuga.extensions

import com.masliaiev.core.models.response.Error
import retrofit2.Response

fun <T> Response<T>.getErrorOrNull(): Error? {
    return if (!isSuccessful){
        Error(
            code = code(),
            message = message()
        )
    } else null
}