package com.develop.zuzik.fragmentnavigation

import android.os.Bundle
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat

/**
 * User: zuzik
 * Date: 12/25/16
 */
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference("screen_preference").onPreferenceClickListener = Preference.OnPreferenceClickListener {
            if (context is NavigationContainer) {
                (context as NavigationContainer).pushChild(SettingsFragment())
            }
            true
        }
    }
}