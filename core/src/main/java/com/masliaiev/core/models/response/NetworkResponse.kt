package com.masliaiev.core.models.response

class NetworkResponse<DATA, ERROR>(
    private val data: DATA?,
    private val error: ERROR? = null
) {

    suspend fun onSuccess(block: suspend (DATA) -> Unit): NetworkResponse<DATA, ERROR> {
        data?.let {
            block(it)
        }
        return this
    }

    suspend fun onError(block: suspend (ERROR) -> Unit) {
        error?.let {
            block(it)
        }
    }
}