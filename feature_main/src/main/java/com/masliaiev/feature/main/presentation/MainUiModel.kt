package com.masliaiev.feature.main.presentation

import com.masliaiev.core.constants.EmptyConstants
import com.masliaiev.core.models.Track
import com.masliaiev.core.ui.base.Effect
import com.masliaiev.core.ui.base.Event
import com.masliaiev.core.ui.base.UiModel
import kotlinx.collections.immutable.ImmutableList

sealed class MainState : UiModel.State {

    object ShowContent : MainState()
}

data class MainData(
    val track: Track?,
    val playlist: ImmutableList<Track>?,
    val currentTrackFullDuration: String,
    val currentTrackCurrentDuration: String,
    val isPlaying: Boolean,
    val progress: Float,
    val playPauseAvailable: Boolean,
    val seekToNextAvailable: Boolean,
    val seekToPreviousAvailable: Boolean
) : UiModel.Data {
    companion object {
        fun default() = MainData(
            track = null,
            playlist = null,
            currentTrackFullDuration = EmptyConstants.EMPTY_STRING,
            currentTrackCurrentDuration = EmptyConstants.EMPTY_STRING,
            isPlaying = false,
            progress = EmptyConstants.EMPTY_FLOAT,
            playPauseAvailable = false,
            seekToNextAvailable = false,
            seekToPreviousAvailable = false
        )
    }
}

sealed class MainEvent : Event {
    object PlayOrPause : MainEvent()

    object SeekToNext : MainEvent()

    object SeekToPrevious : MainEvent()

    class OnPlayerSliderValueChange(val progress: Float) : MainEvent()

    object OnPlayerSliderValueChangeFinished : MainEvent()

    class SeekToTrack(val index: Int) : MainEvent()
}

sealed class MainEffect : Effect {
    class ShowPlayerErrorToast(val message: String) : MainEffect()
}