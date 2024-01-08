package com.masliaiev.feature.playlist.domain.repository

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.Track

interface PlaylistRepository {

    suspend fun getPlayList(playlistId: String): Result<Playlist?>

    fun playTrack(track: Track)

    fun playPlaylist(trackList: List<Track>)
}