package com.masliaiev.feature.home.presentation

import androidx.paging.PagingData
import com.masliaiev.core.base.ScreenState
import com.masliaiev.core.models.Playlist
import kotlinx.coroutines.flow.Flow

data class HomeScreenState(
    val playlists: Flow<PagingData<Playlist>>
) : ScreenState
