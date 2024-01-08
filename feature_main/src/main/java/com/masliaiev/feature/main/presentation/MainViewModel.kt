package com.masliaiev.feature.main.presentation

import androidx.lifecycle.viewModelScope
import com.masliaiev.core.ui.base.BaseViewModel
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
) : BaseViewModel<MainState, MainData, MainEvent, MainEffect>() {

    init {
        subscribeOnPlayerUpdates()
    }

    override fun provideInitialUiModel() = MainState.ShowContent and MainData.default()

    override fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.PlayOrPause -> playOrPauseUseCase.playOrPause()
            is MainEvent.SeekToNext -> seekToNextUseCase.seekToNext()
            is MainEvent.SeekToPrevious -> seekToPreviousUseCase.seekToPrevious()
            is MainEvent.OnPlayerSliderValueChange -> {
                onSliderValueChangeUseCase.onSliderValueChange(
                    event.progress
                )
            }

            is MainEvent.OnPlayerSliderValueChangeFinished -> {
                onSliderValueChangeFinishedUseCase.onSliderValueChangeFinished()
            }

            is MainEvent.SeekToTrack -> seekToTrackUseCase.seekToTrack(event.index)
        }
    }

    private fun subscribeOnPlayerUpdates() {
        playerStateFlowUseCase.playerStateFlow
            .onEach { playerState ->
                updateUiModelData { currentData ->
                    currentData.copy(
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
                playerState.errorMessage?.let {
                    sendEffect(
                        MainEffect.ShowPlayerErrorToast(it)
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}