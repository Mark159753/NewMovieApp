package com.example.movieapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.example.movieapp.R
import com.example.movieapp.until.LocaleHelper
import com.example.movieapp.until.setupDarkLightMode
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var bottomNavigation:BottomNavigationView
    private lateinit var hostFragment:NavHostFragment

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!, LocaleHelper.getLanguage(newBase)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupLightDarkMode()

        bottomNavigation = findViewById(R.id.bottom_navigation)

        hostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = hostFragment.navController

        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }

    private fun setupLightDarkMode(){
        val mode = PreferenceManager.getDefaultSharedPreferences(this).getString("theme_light_dark_mode", "default")
        mode?.let { setupDarkLightMode(it) }
    }
}