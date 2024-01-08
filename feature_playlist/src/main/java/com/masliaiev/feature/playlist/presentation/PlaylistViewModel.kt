package com.masliaiev.feature.playlist.presentation

import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.base.BaseViewModel
import com.masliaiev.core.ui.base.NoEffect
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
) : BaseViewModel<PlaylistState, PlaylistData, PlaylistEvent, NoEffect>() {

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
}