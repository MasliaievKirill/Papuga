package com.masliaiev.feature.home.presentation

import com.masliaiev.core.base.BaseViewModel
import com.masliaiev.core.base.UiEvent
import com.masliaiev.core.base.ViewModelEvent
import com.masliaiev.feature.home.domain.usecases.GetPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlaylistsUseCase: GetPlaylistsUseCase
) : BaseViewModel<HomeScreenState, UiEvent, ViewModelEvent>() {

    init {
        launch {
            val flow = getPlaylistsUseCase.getPlaylists()
            updateState { currentState ->
                currentState.value = HomeScreenState(
                    playlists = flow
                )
            }
        }
    }

    override fun onUiEvent(uiEvent: UiEvent) = Unit
}