package com.example.netia_weatherk.`interface`

import com.example.netia_weatherk.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface NWSInterface {
    @Headers("Accept: application/geo+json")
    @GET("/gridpoints/{office}/{gridX},{gridY}/forecast")
    suspend fun getForecast(
        @Path("office") office: String,
        @Path("gridX") gridX: Int,
        @Path("gridY") gridY: Int
    ): WeatherResponse
}