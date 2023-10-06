package com.masliaiev.feature.playlist.data.repository

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.response.Error
import com.masliaiev.core.models.response.NetworkResponse
import com.masliaiev.feature.playlist.data.provider.PlaylistApiProvider
import com.masliaiev.feature.playlist.domain.repository.PlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val provider: PlaylistApiProvider
) : PlaylistRepository {

    override suspend fun getPlayList(playlistId: String): NetworkResponse<Playlist, Error> {
        return provider.getPlayList(playlistId)
    }
}