package com.masliaiev.api.interceptors

import com.masliaiev.api.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiHeadersInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest.newBuilder()
            .addHeader(BuildConfig.HEADER_API_KEY, BuildConfig.API_KEY)
            .addHeader(BuildConfig.HEADER_API_HOST, BuildConfig.API_HOST)
            .build()

        return chain.proceed(newRequest)
    }
}