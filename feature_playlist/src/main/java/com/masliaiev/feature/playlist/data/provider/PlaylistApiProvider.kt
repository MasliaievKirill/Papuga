package com.masliaiev.feature.playlist.data.provider

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.response.Error
import com.masliaiev.core.models.response.NetworkResponse

interface PlaylistApiProvider {

    suspend fun getPlayList(playlistId: String): NetworkResponse<Playlist, Error>
}