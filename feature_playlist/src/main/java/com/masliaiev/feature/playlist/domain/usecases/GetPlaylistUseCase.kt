package com.masliaiev.feature.playlist.domain.usecases

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.response.Error
import com.masliaiev.core.models.response.NetworkResponse
import com.masliaiev.feature.playlist.domain.repository.PlaylistRepository
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend fun getPlayList(playlistId: String): NetworkResponse<Playlist, Error> {
        return repository.getPlayList(playlistId)
    }
}