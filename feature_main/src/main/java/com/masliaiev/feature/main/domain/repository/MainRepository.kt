package com.masliaiev.feature.main.domain.repository

import androidx.paging.PagingData
import com.masliaiev.core.models.Playlist
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun start(): Flow<PagingData<Playlist>>
}