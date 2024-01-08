package com.masliaiev.api.di

import com.masliaiev.api.ApiFactory
import com.masliaiev.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {

    companion object {
        @Provides
        @Singleton
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}