package com.develop.zuzik.fragmentnavigation.navigation.list

import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry

/**
 * User: zuzik
 * Date: 12/24/16
 */
class ListNavigationFragmentFactory(private val entries: List<NavigationEntry>) : FragmentFactory {

    override fun create() = ListNavigationFragment.create(entries)
}