package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry
import com.develop.zuzik.fragmentnavigation.tabs_navigation_fragment.TabsNavigationFragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
class TabsNavigationFragmentFactory(private val entries: List<NavigationEntry>) : FragmentFactory {

    override fun create() = TabsNavigationFragment.create(entries)
}