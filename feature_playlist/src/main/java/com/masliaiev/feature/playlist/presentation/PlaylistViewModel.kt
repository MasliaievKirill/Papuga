package com.masliaiev.feature.playlist.presentation

import androidx.lifecycle.viewModelScope
import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.base.BaseViewModel
import com.masliaiev.core.ui.base.NoEffect
import com.masliaiev.feature.playlist.domain.usecases.GetPlaylistUseCase
import com.masliaiev.feature.playlist.domain.usecases.PlayPlaylistUseCase
import com.masliaiev.feature.playlist.domain.usecases.PlayTrackUseCase
import com.masliaiev.feature.playlist.domain.usecases.PlayerStateFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel @Inject constructor(
    private val getPlaylistUseCase: GetPlaylistUseCase,
    private val playTrackUseCase: PlayTrackUseCase,
    private val playPlaylistUseCase: PlayPlaylistUseCase,
    private val playerStateFlowUseCase: PlayerStateFlowUseCase
) : BaseViewModel<PlaylistState, PlaylistData, PlaylistEvent, NoEffect>() {

    init {
        subscribeOnPlayerUpdates()
    }

    override fun provideInitialUiModel() = PlaylistState.DataLoading and PlaylistData.default()

    override fun onEvent(event: PlaylistEvent) {
        when (event) {
            is PlaylistEvent.LoadPlaylist -> loadPlaylist(event.playlistId)
            is PlaylistEvent.OnTrackClick -> onTrackClick(event.track)
            is PlaylistEvent.OnPlayPlaylistClick -> onPlayPlaylistClick(event.trackList)
        }
    }

    private fun loadPlaylist(playlistId: String) {
        launch {
            getPlaylistUseCase.getPlayList(playlistId)
                .onSuccess { playlist ->
                    updateUiModel(
                        state = {
                            PlaylistState.DataLoaded
                        },
                        data = {
                            it.copy(
                                playlist = playlist
                            )
                        }
                    )
                }
        }
    }

    private fun onTrackClick(track: Track) {
        playTrackUseCase.playTrack(track)
    }

    private fun onPlayPlaylistClick(trackList: List<Track>) {
        playPlaylistUseCase.playPlaylist(trackList)
    }

    private fun subscribeOnPlayerUpdates() {
        playerStateFlowUseCase.playerStateFlow
            .onEach { playerState ->
                updateUiModelData { currentData ->
                    currentData.copy(
                        currentTrack = playerState.track,
                        playerIsPlaying = playerState.isPlaying
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}