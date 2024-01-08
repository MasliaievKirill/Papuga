package com.masliaiev.feature.home.presentation

import com.masliaiev.core.ui.base.BaseViewModel
import com.masliaiev.core.ui.base.NoEffect
import com.masliaiev.core.ui.base.NoEvent
import com.masliaiev.feature.home.domain.usecases.GetPlaylistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlaylistsUseCase: GetPlaylistsUseCase
) : BaseViewModel<HomeState, HomeData, NoEvent, NoEffect>() {

    init {
        launch {
            val flow = getPlaylistsUseCase.getPlaylists()
            updateUiModelData(
                data = {
                    it.copy(
                        playlists = flow
                    )
                }
            )
        }
    }

    override fun provideInitialUiModel() = HomeState.DataLoaded and HomeData.default()


    override fun onEvent(event: NoEvent) = Unit
}