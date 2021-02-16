package com.example.movieapp.ui.settings

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.room.withTransaction
import com.example.movieapp.MovieApp
import com.example.movieapp.R
import com.example.movieapp.data.local.MovieDb
import com.example.movieapp.until.LocaleHelper
import com.example.movieapp.until.setupDarkLightMode
import kotlinx.coroutines.*
import javax.inject.Inject


class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var uiModeList:ListPreference
    private lateinit var languagePrefList:ListPreference

    private val dbScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Inject
    lateinit var db: MovieDb

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectMe()

        uiModeList = preferenceManager.findPreference<ListPreference>("theme_light_dark_mode")!!
        if(uiModeList.value == null) uiModeList.value = "default"
        uiModeList.setOnPreferenceChangeListener { _, newValue ->
            setupDarkLightMode(newValue.toString())
            return@setOnPreferenceChangeListener true
        }
        languagePrefList = preferenceManager.findPreference<ListPreference>("language")!!
        if(languagePrefList.value == null) languagePrefList.value = LocaleHelper.getLanguage(requireContext())
        languagePrefList.setOnPreferenceChangeListener { _, newValue ->
            LocaleHelper.setLocale(requireContext(), newValue.toString())
            dbScope.launch {
                db.clearAllTables()
            }
            activity?.recreate()
            return@setOnPreferenceChangeListener true
        }
    }

    private fun injectMe(){
        (activity?.application as MovieApp).getFragmentComponent()
                .create()
                .inject(this)
    }

    override fun onDestroy() {
        dbScope.cancel()
        super.onDestroy()
    }

}