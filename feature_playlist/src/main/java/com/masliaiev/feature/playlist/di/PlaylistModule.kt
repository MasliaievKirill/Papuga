package com.masliaiev.feature.playlist.di

import com.masliaiev.feature.playlist.data.repository.PlaylistRepositoryImpl
import com.masliaiev.feature.playlist.domain.repository.PlaylistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PlaylistModule {

    @Binds
    @Singleton
    fun bindPlaylistRepository(playlistRepositoryImpl: PlaylistRepositoryImpl): PlaylistRepository
}