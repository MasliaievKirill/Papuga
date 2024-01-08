package com.masliaiev.core.data

import com.masliaiev.core.models.BadRequestThrowable
import com.masliaiev.core.models.ForbiddenThrowable
import com.masliaiev.core.models.NotFoundThrowable
import com.masliaiev.core.models.UnauthorizedThrowable
import com.masliaiev.core.models.UndefinedThrowable
import retrofit2.Response

suspend fun <T, R> makeRequest(
    request: suspend () -> Response<R>,
    onSuccess: (body: R?) -> T,
    onError: ((code: Int) -> Throwable)? = null
): Result<T> {
    return request().run {
        when {
            isSuccessful -> Result.success(onSuccess(this.body()))

            code() == BAD_REQUEST_ERROR_CODE -> throw BadRequestThrowable

            code() == UNAUTHORIZED_ERROR_CODE -> throw UnauthorizedThrowable

            code() == FORBIDDEN_ERROR_CODE -> throw ForbiddenThrowable

            code() == NOT_FOUND_ERROR_CODE -> throw NotFoundThrowable

            else -> {
                if (onError == null) {
                    throw UndefinedThrowable
                } else {
                    Result.failure(onError(this.code()))
                }
            }
        }
    }
}

private const val BAD_REQUEST_ERROR_CODE = 400
private const val UNAUTHORIZED_ERROR_CODE = 401
private const val FORBIDDEN_ERROR_CODE = 403
private const val NOT_FOUND_ERROR_CODE = 404