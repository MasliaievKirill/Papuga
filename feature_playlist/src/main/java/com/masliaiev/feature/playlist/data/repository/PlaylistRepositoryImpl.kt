package com.masliaiev.feature.playlist.data.repository

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.Track
import com.masliaiev.core.models.player.Player
import com.masliaiev.feature.playlist.data.provider.PlaylistApiProvider
import com.masliaiev.feature.playlist.domain.repository.PlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val provider: PlaylistApiProvider,
    private val player: Player
) : PlaylistRepository {

    override suspend fun getPlayList(playlistId: String): Result<Playlist?> {
        return provider.getPlayList(playlistId)
    }

    override fun playTrack(track: Track) {
        player.playTrack(track)
    }

    override fun playPlaylist(trackList: List<Track>) {
        player.playPlaylist(trackList)
    }
}