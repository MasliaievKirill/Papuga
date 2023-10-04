package com.masliaiev.papuga.di

import com.masliaiev.feature.main.data.provider.MainApiProvider
import com.masliaiev.feature.main.data.repository.MainRepositoryImpl
import com.masliaiev.feature.main.domain.repository.MainRepository
import com.masliaiev.papuga.providers.MainApiProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProviderModule {

    @Binds
    @Singleton
    fun bindMainApiProvider(mainApiProviderImpl: MainApiProviderImpl): MainApiProvider

}