package com.masliaiev.api.interceptors

import com.masliaiev.api.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiHeadersInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestUrl = originalRequest.url().newBuilder()
            .addQueryParameter(BuildConfig.HEADER_API_KEY, BuildConfig.API_KEY)
            .addQueryParameter(BuildConfig.HEADER_API_HOST, BuildConfig.API_HOST)
            .build()

        val request = originalRequest.newBuilder().url(requestUrl).build()

        return chain.proceed(request)
    }
}