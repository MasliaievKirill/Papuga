package com.masliaiev.feature.playlist.domain.repository

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.response.Error
import com.masliaiev.core.models.response.NetworkResponse

interface PlaylistRepository {

    suspend fun getPlayList(playlistId: String): NetworkResponse<Playlist, Error>
}