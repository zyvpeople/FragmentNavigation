package com.develop.zuzik.fragmentnavigation.navigation.pager

import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry

/**
 * User: zuzik
 * Date: 12/24/16
 */
class PagerNavigationFragmentFactory(private val entries: List<NavigationEntry>) : FragmentFactory {

    override fun create() = PagerNavigationFragment.create(entries)
}