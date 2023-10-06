package com.masliaiev.feature.home.di

import com.masliaiev.feature.home.data.repository.HomeRepositoryImpl
import com.masliaiev.feature.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface HomeModule {

    @Binds
    @Singleton
    fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository
}