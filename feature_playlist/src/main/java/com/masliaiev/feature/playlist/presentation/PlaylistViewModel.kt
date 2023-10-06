package com.masliaiev.feature.playlist.presentation

import com.masliaiev.core.base.BaseViewModel
import com.masliaiev.feature.playlist.domain.usecases.GetPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase
) : BaseViewModel<PlaylistScreenState, PlaylistUiEvent, PlaylistViewModelEvent>() {

    override fun onUiEvent(uiEvent: PlaylistUiEvent) {
        when (uiEvent) {
            is PlaylistUiEvent.LoadPlaylist -> loadPlaylist(uiEvent.playlistId)
        }
    }

    private fun loadPlaylist(playlistId: String) {
        launch {
            getPlaylistUseCase.getPlayList(playlistId)
                .onSuccess {
                    updateState { currentState ->
                        currentState.value = PlaylistScreenState(
                            playlist = it
                        )
                    }
                }
                .onError {
                    sendViewModelEvent(PlaylistViewModelEvent.PlaylistLoadingError)
                }
        }
    }
}