package com.minutiello.thegallery.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.minutiello.thegallery.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}