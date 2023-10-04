package com.masliaiev.feature.main.data.repository

import com.masliaiev.feature.main.data.provider.MainApiProvider
import com.masliaiev.feature.main.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val provider: MainApiProvider
) : MainRepository {

    override fun start() {
        provider.start()
    }
}