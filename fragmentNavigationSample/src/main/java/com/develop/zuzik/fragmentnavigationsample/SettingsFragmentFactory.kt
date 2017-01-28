package com.develop.zuzik.fragmentnavigationsample

import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory

/**
 * User: zuzik
 * Date: 1/4/17
 */
class SettingsFragmentFactory : FragmentFactory {
    override fun create(path: List<String>) = SettingsFragment()
}