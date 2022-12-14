package com.example.weather.ui.weather_report

import com.example.weather.api.ApiInterface
import javax.inject.Inject

class WeatherReportRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    /*TODO uncomment private val database: AppDatabase*/
) {

    suspend fun getWeatherReport(latitude : String, longitude : String) = apiInterface.getWeatherReport(latitude, longitude)

}