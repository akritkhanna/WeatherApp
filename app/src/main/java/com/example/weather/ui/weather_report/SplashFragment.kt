package com.example.weather.ui.weather_report

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.weather.R
import com.example.weather.core.BaseFragment
import com.example.weather.databinding.FragmentSplashBinding
import com.example.weather.utils.Constants.BUNDLE_WEATHER
import com.example.weather.utils.Constants.UNIT_METRIC
import com.example.weather.utils.Resource
import com.example.weather.utils.Tools.convertToJsonString
import com.example.weather.utils.Tools.getLastKnownLocation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel by viewModels<WeatherReportViewModel>()

    override fun onResume() {
        super.onResume()

        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED) {
            //TODO call the api..
        }

    }

    override fun initView() {
        requestLocationPermission.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
    }


    override fun initListener() {

    }

    override fun addObserver() {
        viewModel.weatherReportLiveData.observe(viewLifecycleOwner){
            if (it != null){
                binding.pb.isVisible = it is Resource.Loading
                when(it){
                    is Resource.Success -> {
                        navigate(R.id.action_splashFragment_to_weatherReportFragment, Bundle().apply {
                            putString(BUNDLE_WEATHER, it.data?.convertToJsonString())
                        })
                    }
                    is Resource.Error -> {
                        showErrorMessage(it.message)
                    }
                    else -> {}
                }
            }
        }
    }

    val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {

            if (it?.get(Manifest.permission.ACCESS_FINE_LOCATION) == true) {
               context?.getLastKnownLocation()?.let { location ->
                   viewModel.getWeatherReport(location.latitude.toString(), location.longitude.toString(), UNIT_METRIC)
               }
            } else {
                showErrorMessage("Please grant location permission.")
            }

        }


}