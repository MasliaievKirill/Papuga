package com.masliaiev.feature.playlist.domain.repository

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.Track
import com.masliaiev.core.models.response.Error
import com.masliaiev.core.models.response.NetworkResponse

interface PlaylistRepository {

    suspend fun getPlayList(playlistId: String): NetworkResponse<Playlist, Error>

    fun playTrack(track: Track)

    fun playPlaylist(trackList: List<Track>)
}