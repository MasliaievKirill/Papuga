package com.masliaiev.feature.main.presentation

import com.masliaiev.core.base.UiEvent

sealed class MainUiEvent : UiEvent {
    object PlayOrPause : MainUiEvent()

    object SeekToNext : MainUiEvent()

    object SeekToPrevious : MainUiEvent()

    class OnPlayerSliderValueChange(val progress: Float) : MainUiEvent()

    object OnPlayerSliderValueChangeFinished : MainUiEvent()

    class SeekToTrack(val index: Int) : MainUiEvent()
}
