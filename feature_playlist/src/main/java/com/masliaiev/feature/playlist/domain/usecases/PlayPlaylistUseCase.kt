package com.masliaiev.feature.playlist.domain.usecases

import com.masliaiev.core.models.Track
import com.masliaiev.feature.playlist.domain.repository.PlaylistRepository
import javax.inject.Inject

class PlayPlaylistUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    fun playPlaylist(trackList: List<Track>) {
        repository.playPlaylist(trackList)
    }
}