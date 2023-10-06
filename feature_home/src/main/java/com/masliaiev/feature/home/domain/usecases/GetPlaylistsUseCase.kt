package com.masliaiev.feature.home.domain.usecases

import androidx.paging.PagingData
import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlaylistsUseCase @Inject constructor(
    private val repository: HomeRepository
) {
    suspend fun getPlaylists(): Flow<PagingData<Playlist>> {
        return repository.getPlaylists()
    }
}