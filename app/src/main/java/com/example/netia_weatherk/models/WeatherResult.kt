package com.example.netia_weatherk.models

sealed class WeatherResult {
    data class Content(val forecasts: List<Forecast>) : WeatherResult()
    data class Error(val throwable: Throwable) : WeatherResult()
}