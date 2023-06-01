package com.example.netia_weatherk.utils

import com.example.netia_weatherk.`interface`.NWSInterface
import com.example.netia_weatherk.models.WeatherResult
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WeatherRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://api.weather.gov")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(NWSInterface::class.java)

    suspend fun load(office: String, gridX: Int, gridY: Int) = try {
        val response = api.getForecast(office, gridX, gridY)

        WeatherResult.Content(response.properties?.periods ?: listOf())
    } catch (t: Throwable) {
        WeatherResult.Error(t)
    }
}
