package com.masliaiev.feature.playlist.presentation

import com.masliaiev.core.base.ViewModelEvent

sealed class PlaylistViewModelEvent : ViewModelEvent {
    object PlaylistLoadingError : PlaylistViewModelEvent()
}
