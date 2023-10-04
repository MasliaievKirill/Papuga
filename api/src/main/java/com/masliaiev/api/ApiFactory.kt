package com.masliaiev.api

import com.masliaiev.api.interceptors.ApiHeadersInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = BuildConfig.BASE_URL
    private val okHttpClient = OkHttpClient.Builder().addInterceptor(ApiHeadersInterceptor()).build()

    private val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}