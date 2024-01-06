package com.masliaiev.feature.playlist.presentation

import com.masliaiev.core.base.UiEvent
import com.masliaiev.core.models.Track

sealed class PlaylistUiEvent : UiEvent {
    class LoadPlaylist(val playlistId: String) : PlaylistUiEvent()
    class OnTrackClick(val track: Track) : PlaylistUiEvent()
    class OnPlayPlaylistClick(val trackList: List<Track>) : PlaylistUiEvent()
}
