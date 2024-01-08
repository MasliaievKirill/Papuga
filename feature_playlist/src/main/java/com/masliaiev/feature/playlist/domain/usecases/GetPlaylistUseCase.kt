package com.masliaiev.feature.playlist.domain.usecases

import com.masliaiev.core.models.Playlist
import com.masliaiev.feature.playlist.domain.repository.PlaylistRepository
import javax.inject.Inject

class GetPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    suspend fun getPlayList(playlistId: String): Result<Playlist?> {
        return repository.getPlayList(playlistId)
    }
}