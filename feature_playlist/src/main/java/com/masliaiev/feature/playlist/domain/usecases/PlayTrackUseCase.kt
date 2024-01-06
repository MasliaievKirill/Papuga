package com.masliaiev.feature.playlist.domain.usecases

import com.masliaiev.core.models.Track
import com.masliaiev.feature.playlist.domain.repository.PlaylistRepository
import javax.inject.Inject

class PlayTrackUseCase @Inject constructor(
    private val repository: PlaylistRepository
) {
    fun playTrack(track: Track) {
        repository.playTrack(track)
    }
}