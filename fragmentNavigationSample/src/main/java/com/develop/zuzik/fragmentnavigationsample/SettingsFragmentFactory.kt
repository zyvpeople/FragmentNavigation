package com.develop.zuzik.fragmentnavigationsample

import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory

/**
 * User: zuzik
 * Date: 1/4/17
 */
class SettingsFragmentFactory : FragmentFactory {
    override fun create() = SettingsFragment()
}