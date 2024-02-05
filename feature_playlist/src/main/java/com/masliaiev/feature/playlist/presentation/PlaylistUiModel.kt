package com.masliaiev.feature.playlist.presentation

import com.masliaiev.core.models.Playlist
import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.base.Event
import com.masliaiev.core.ui.base.UiModel

sealed class PlaylistState : UiModel.State {

    object DataLoading : PlaylistState()

    object DataLoaded : PlaylistState()
}

data class PlaylistData(
    val playlist: Playlist?,
    val currentTrack: Track?,
    val playerIsPlaying: Boolean
) : UiModel.Data {

    companion object {
        fun default() = PlaylistData(
            playlist = null,
            currentTrack = null,
            playerIsPlaying = false
        )
    }
}

sealed class PlaylistEvent : Event {
    class LoadPlaylist(val playlistId: String) : PlaylistEvent()
    class OnTrackClick(val track: Track) : PlaylistEvent()
    class OnPlayPlaylistClick(val trackList: List<Track>) : PlaylistEvent()
}