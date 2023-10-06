package com.masliaiev.feature.home.domain.repository

import androidx.paging.PagingData
import com.masliaiev.core.models.Playlist
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    suspend fun getPlaylists(): Flow<PagingData<Playlist>>
}