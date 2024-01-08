package com.masliaiev.feature.playlist.data.provider

import com.masliaiev.core.models.Playlist

interface PlaylistApiProvider {

    suspend fun getPlayList(playlistId: String): Result<Playlist?>
}