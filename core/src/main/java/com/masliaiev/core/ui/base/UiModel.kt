package com.masliaiev.core.ui.base

data class UiModel<D : UiModel.Data>(
    val state: State,
    val data: D
) {
    interface State
    interface Data
}

interface Event

interface Effect

object NoEvent : Event

object NoEffect : Effect