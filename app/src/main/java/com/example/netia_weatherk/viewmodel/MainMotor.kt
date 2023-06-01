package com.example.netia_weatherk.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.netia_weatherk.R
import com.example.netia_weatherk.models.MainViewState
import com.example.netia_weatherk.models.RowState
import com.example.netia_weatherk.models.WeatherResult
import com.example.netia_weatherk.utils.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainMotor(application: Application) : AndroidViewModel(application) {
    private val _results = MutableLiveData<MainViewState>()
    val results: LiveData<MainViewState> = _results

    fun load(office: String, gridX: Int, gridY: Int) {
        _results.value = MainViewState.Loading

        viewModelScope.launch(Dispatchers.Main) {
            val result = WeatherRepository.load(office, gridX, gridY)

            _results.value = when (result) {
                is WeatherResult.Content -> {
                    val rows = result.forecasts.map { forecast ->
                        val temp = getApplication<Application>()
                            .getString(
                                R.string.temp,
                                forecast.temperature,
                                forecast.temperatureUnit
                            )

                        RowState(forecast.name, temp, forecast.icon)
                    }

                    MainViewState.Content(rows)
                }
                is WeatherResult.Error -> MainViewState.Error(result.throwable)
            }
        }
    }
}