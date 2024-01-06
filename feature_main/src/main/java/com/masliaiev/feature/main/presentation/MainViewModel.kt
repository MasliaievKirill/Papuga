package com.masliaiev.feature.main.presentation

import androidx.lifecycle.viewModelScope
import com.masliaiev.core.base.BaseViewModel
import com.masliaiev.feature.main.domain.usecases.OnSliderValueChangeFinishedUseCase
import com.masliaiev.feature.main.domain.usecases.OnSliderValueChangeUseCase
import com.masliaiev.feature.main.domain.usecases.PlayOrPauseUseCase
import com.masliaiev.feature.main.domain.usecases.PlayerStateFlowUseCase
import com.masliaiev.feature.main.domain.usecases.SeekToNextUseCase
import com.masliaiev.feature.main.domain.usecases.SeekToPreviousUseCase
import com.masliaiev.feature.main.domain.usecases.SeekToTrackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val playerStateFlowUseCase: PlayerStateFlowUseCase,
    private val playOrPauseUseCase: PlayOrPauseUseCase,
    private val seekToNextUseCase: SeekToNextUseCase,
    private val seekToPreviousUseCase: SeekToPreviousUseCase,
    private val onSliderValueChangeUseCase: OnSliderValueChangeUseCase,
    private val onSliderValueChangeFinishedUseCase: OnSliderValueChangeFinishedUseCase,
    private val seekToTrackUseCase: SeekToTrackUseCase
) : BaseViewModel<MainScreenState, MainUiEvent, MainViewModelEvent>() {

    init {
        playerStateFlowUseCase.playerStateFlow
            .onEach { playerState ->
                updateState { currentState ->
                    currentState.value?.let {
                        currentState.value = it.copy(
                            track = playerState.track,
                            isPlaying = playerState.isPlaying,
                            playPauseAvailable = playerState.playPauseAvailable,
                            seekToNextAvailable = playerState.seekToNextAvailable,
                            seekToPreviousAvailable = playerState.seekToPreviousAvailable,
                            currentTrackFullDuration = playerState.fullDuration,
                            currentTrackCurrentDuration = playerState.currentDuration,
                            progress = playerState.progress
                        )
                    } ?: run {
                        currentState.value = MainScreenState(
                            track = playerState.track,
                            isPlaying = playerState.isPlaying,
                            playPauseAvailable = playerState.playPauseAvailable,
                            seekToNextAvailable = playerState.seekToNextAvailable,
                            seekToPreviousAvailable = playerState.seekToPreviousAvailable,
                            currentTrackFullDuration = playerState.fullDuration,
                            currentTrackCurrentDuration = playerState.currentDuration,
                            progress = playerState.progress
                        )
                    }
                }
                playerState.errorMessage?.let {
                    sendViewModelEvent(
                        MainViewModelEvent.ShowPlayerErrorToast(it)
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onUiEvent(uiEvent: MainUiEvent) {
        when (uiEvent) {
            is MainUiEvent.PlayOrPause -> playOrPauseUseCase.playOrPause()
            is MainUiEvent.SeekToNext -> seekToNextUseCase.seekToNext()
            is MainUiEvent.SeekToPrevious -> seekToPreviousUseCase.seekToPrevious()
            is MainUiEvent.OnPlayerSliderValueChange -> {
                onSliderValueChangeUseCase.onSliderValueChange(
                    uiEvent.progress
                )
            }

            is MainUiEvent.OnPlayerSliderValueChangeFinished -> {
                onSliderValueChangeFinishedUseCase.onSliderValueChangeFinished()
            }

            is MainUiEvent.SeekToTrack -> seekToTrackUseCase.seekToTrack(uiEvent.index)
        }
    }
}