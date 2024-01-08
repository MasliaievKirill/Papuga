package com.masliaiev.player.di

import com.masliaiev.core.models.player.Player
import com.masliaiev.player.PlayerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface PlayerModule {

    @Binds
    @Singleton
    fun bindPlayer(playerImpl: PlayerImpl): Player

}