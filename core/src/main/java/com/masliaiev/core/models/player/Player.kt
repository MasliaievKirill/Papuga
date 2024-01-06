package com.masliaiev.core.models.player

import android.content.Context
import androidx.lifecycle.Lifecycle
import com.masliaiev.core.models.Track
import kotlinx.coroutines.flow.StateFlow

interface Player {

    val playerStateFlow: StateFlow<PlayerState>

    fun initialize(context: Context, lifecycle: Lifecycle)

    fun playTrack(track: Track)

    fun playPlaylist(playlist: List<Track>)

    fun playOrPause()

    fun seekToNext()

    fun seekToPrevious()

    fun seekToTrack(index: Int)

    fun onSliderValueChange(progress: Float)

    fun onSliderValueChangeFinished()
}