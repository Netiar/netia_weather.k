package com.example.netia_weatherk.models

sealed class MainViewState {
    object Loading : MainViewState()
    data class Content(val forecasts: List<RowState>) : MainViewState()
    data class Error(val throwable: Throwable) : MainViewState()
}