package com.masliaiev.feature.playlist.presentation

import com.masliaiev.core.base.ScreenState
import com.masliaiev.core.models.Playlist

data class PlaylistScreenState(
    val playlist: Playlist
) : ScreenState
