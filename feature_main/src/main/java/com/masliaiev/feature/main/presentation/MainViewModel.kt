package com.masliaiev.feature.main.presentation

import com.masliaiev.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

) : BaseViewModel<MainScreenState, MainEvent, MainMessage>() {

}