package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry

/**
 * User: zuzik
 * Date: 12/24/16
 */
class StackNavigationFragmentFactory(private val entries: List<NavigationEntry>) : FragmentFactory {

    override fun create() = StackNavigationFragment.create(entries)
}