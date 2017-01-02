package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import com.develop.zuzik.fragmentnavigation.navigation_fragment.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class CompositeStackNavigationFragmentFactory : CompositeFragmentFactory {

    override fun create(entries: List<NavigationEntry>): FragmentFactory {
        return StackNavigationFragmentFactory(entries)
    }
}