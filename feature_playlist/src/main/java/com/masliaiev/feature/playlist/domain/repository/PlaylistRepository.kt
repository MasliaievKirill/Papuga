package com.masliaiev.feature.playlist.domain.repository

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.Track
import com.masliaiev.core.models.player.PlayerState
import kotlinx.coroutines.flow.StateFlow

interface PlaylistRepository {

    val playerStateFlow: StateFlow<PlayerState>

    suspend fun getPlayList(playlistId: String): Result<Playlist?>

    fun playTrack(track: Track)

    fun playPlaylist(trackList: List<Track>)
}