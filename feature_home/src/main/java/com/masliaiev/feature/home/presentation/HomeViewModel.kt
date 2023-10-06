package com.masliaiev.feature.home.presentation

import androidx.lifecycle.viewModelScope
import com.masliaiev.core.base.BaseViewModel
import com.masliaiev.core.base.Event
import com.masliaiev.core.base.Message
import com.masliaiev.feature.home.domain.usecases.GetPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val startUseCase: GetPlaylistsUseCase
) : BaseViewModel<HomeScreenState, Event, Message>() {

    init {
        viewModelScope.launch {
            val flow = startUseCase.getPlaylists()
            updateState { currentState ->
                currentState.value = HomeScreenState(
                    playlists = flow
                )
            }
        }
    }
}