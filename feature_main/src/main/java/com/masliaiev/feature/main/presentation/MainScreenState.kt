package com.masliaiev.feature.main.presentation

import androidx.paging.PagingData
import com.masliaiev.core.base.ScreenState
import com.masliaiev.core.models.Playlist
import kotlinx.coroutines.flow.Flow

data class MainScreenState(
    val playlists: Flow<PagingData<Playlist>>
) : ScreenState
