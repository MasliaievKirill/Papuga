package com.masliaiev.papuga.providers

import com.masliaiev.api.ApiService
import com.masliaiev.feature.main.data.provider.MainApiProvider
import javax.inject.Inject

class MainApiProviderImpl @Inject constructor(
    private val apiService: ApiService
): MainApiProvider {

    override fun start() {

    }
}