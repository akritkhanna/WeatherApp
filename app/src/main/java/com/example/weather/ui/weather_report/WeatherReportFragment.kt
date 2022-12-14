package com.example.weather.ui.weather_report

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.weather.core.BaseFragment
import com.example.weather.databinding.FragmentWeatherReportBinding
import com.example.weather.models.WeatherReport
import com.example.weather.utils.Constants.BUNDLE_WEATHER
import com.example.weather.utils.Constants.UNIT_METRIC
import com.example.weather.utils.Resource
import com.example.weather.utils.Tools.getLastKnownLocation
import com.google.android.material.color.MaterialColors
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherReportFragment :
    BaseFragment<FragmentWeatherReportBinding>(FragmentWeatherReportBinding::inflate) {

    private val viewModel by viewModels<WeatherReportViewModel>()

    //private var report: WeatherReport? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setWeatherReport(report : WeatherReport) {
        binding.tvTitle.text = report.name
    }


    override fun initView() {

        arguments?.getString(BUNDLE_WEATHER)?.let {
            try {
                val report = Gson().fromJson(it, WeatherReport::class.java)
                setWeatherReport(report)
            } catch (jse: JsonSyntaxException) {

            }

        }

        view?.let {
            binding.swipeContainer.setColorSchemeColors(
                MaterialColors.getColor(
                    it,
                    androidx.appcompat.R.attr.colorPrimary
                ), MaterialColors.getColor(it, androidx.appcompat.R.attr.colorPrimaryDark)
            )
        }


    }

    override fun initListener() {

        binding.ivBack.setOnClickListener {
           activity?.onBackPressed()
        }

        binding.ivSave.setOnClickListener {
            //TODO imple
        }

        binding.swipeContainer.setOnRefreshListener {
            context?.getLastKnownLocation()?.let { location ->
                viewModel.getWeatherReport(
                    location.latitude.toString(),
                    location.longitude.toString(),
                    UNIT_METRIC
                )
            }
        }
    }

    override fun addObserver() {
        viewModel.weatherReportLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.swipeContainer.isRefreshing = it is Resource.Loading
                when (it) {
                    is Resource.Success -> {

                        it.data?.let { report -> setWeatherReport(report) }
                    }
                    is Resource.Error -> {
                        showErrorMessage(it.message)
                    }
                    else -> {}
                }
            }
        }
    }
}