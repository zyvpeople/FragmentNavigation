package com.develop.zuzik.fragmentnavigation.navigation.pager

import com.develop.zuzik.fragmentnavigation.navigation.interfaces.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry

/**
 * User: zuzik
 * Date: 12/25/16
 */
class CompositePagerNavigationFragmentFactory : CompositeFragmentFactory {

    override fun create(entries: List<NavigationEntry>): FragmentFactory {
        return PagerNavigationFragmentFactory(entries)
    }
}