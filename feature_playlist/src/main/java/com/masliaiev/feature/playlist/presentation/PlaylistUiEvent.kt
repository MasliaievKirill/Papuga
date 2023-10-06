package com.masliaiev.feature.playlist.presentation

import com.masliaiev.core.base.UiEvent

sealed class PlaylistUiEvent : UiEvent {
    class LoadPlaylist(val playlistId: String) : PlaylistUiEvent()
}
