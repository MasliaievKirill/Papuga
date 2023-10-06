package com.masliaiev.papuga.di

import com.masliaiev.feature.home.data.provider.HomeApiProvider
import com.masliaiev.feature.main.data.provider.MainApiProvider
import com.masliaiev.feature.playlist.data.provider.PlaylistApiProvider
import com.masliaiev.papuga.providers.HomeApiProviderImpl
import com.masliaiev.papuga.providers.MainApiProviderImpl
import com.masliaiev.papuga.providers.PlaylistApiProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ProviderModule {

    @Binds
    @Singleton
    fun bindMainApiProvider(mainApiProviderImpl: MainApiProviderImpl): MainApiProvider

    @Binds
    @Singleton
    fun bindHomeApiProvider(homeApiProviderImpl: HomeApiProviderImpl): HomeApiProvider

    @Binds
    @Singleton
    fun bindPlaylistApiProvider(playlistApiProviderImpl: PlaylistApiProviderImpl): PlaylistApiProvider

}