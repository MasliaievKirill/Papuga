package com.masliaiev.feature.playlist.presentation

import com.masliaiev.core.base.BaseViewModel
import com.masliaiev.core.models.Track
import com.masliaiev.feature.playlist.domain.usecases.GetPlaylistUseCase
import com.masliaiev.feature.playlist.domain.usecases.PlayPlaylistUseCase
import com.masliaiev.feature.playlist.domain.usecases.PlayTrackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val playTrackUseCase: PlayTrackUseCase,
    private val playPlaylistUseCase: PlayPlaylistUseCase
) : BaseViewModel<PlaylistScreenState, PlaylistUiEvent, PlaylistViewModelEvent>() {

    override fun onUiEvent(uiEvent: PlaylistUiEvent) {
        when (uiEvent) {
            is PlaylistUiEvent.LoadPlaylist -> loadPlaylist(uiEvent.playlistId)
            is PlaylistUiEvent.OnTrackClick -> onTrackClick(uiEvent.track)
            is PlaylistUiEvent.OnPlayPlaylistClick -> onPlayPlaylistClick(uiEvent.trackList)
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

    private fun onTrackClick(track: Track) {
        playTrackUseCase.playTrack(track)
    }

    private fun onPlayPlaylistClick(trackList: List<Track>) {
        playPlaylistUseCase.playPlaylist(trackList)
    }
}