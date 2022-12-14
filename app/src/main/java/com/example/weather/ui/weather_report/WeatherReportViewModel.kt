package com.example.weather.ui.weather_report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.models.WeatherReport
import com.example.weather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherReportViewModel @Inject constructor(private val repository: WeatherReportRepository) : ViewModel() {

    private val _weatherReportLiveData by lazy { MutableLiveData<Resource<WeatherReport>>() }
    val weatherReportLiveData : LiveData<Resource<WeatherReport>>
        get() = _weatherReportLiveData

    fun getWeatherReport(latitude : String, longitude : String, units : String) = viewModelScope.launch {
        _weatherReportLiveData.postValue(Resource.Loading())
        val response = repository.getWeatherReport(latitude, longitude, units)
        if (response.isSuccessful) {
            response.body()?.let {
                _weatherReportLiveData.postValue(Resource.Success(it))
            }

        } else {
            _weatherReportLiveData.postValue(Resource.Error(response.message(), response.code()))
        }
    }

}