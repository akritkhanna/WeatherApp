package com.example.weather.ui

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.navigation.fragment.NavHostFragment
import com.example.weather.R
import com.example.weather.core.BaseActivity
import com.example.weather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>(), LocationListener {

    private var binding: ActivityMainBinding? = null
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment


    }

    override fun getViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun addObserver() {
    }


    override fun onLocationChanged(p0: Location) {

    }

    override fun onDestroy() {
        super.onDestroy()

    }
}