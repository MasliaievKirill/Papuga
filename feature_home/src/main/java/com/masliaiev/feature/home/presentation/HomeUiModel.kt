package com.masliaiev.feature.home.presentation

import androidx.paging.PagingData
import com.masliaiev.core.models.Playlist
import com.masliaiev.core.ui.base.UiModel
import kotlinx.coroutines.flow.Flow

sealed class HomeState : UiModel.State {
    object DataLoaded : HomeState()
}

data class HomeData(
    val playlists: Flow<PagingData<Playlist>>?
) : UiModel.Data {

    companion object {
        fun default() = HomeData(
            playlists = null
        )
    }
}