package com.develop.zuzik.fragmentnavigationsample

import com.develop.zuzik.fragmentnavigation.model.fragment.ModelFragmentFactory

/**
 * User: zuzik
 * Date: 1/4/17
 */
class SettingsFragmentFactory : ModelFragmentFactory {
    override fun create(path: List<String>) = SettingsFragment()
}