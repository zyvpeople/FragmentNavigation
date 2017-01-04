package com.develop.zuzik.fragmentnavigation.list_navigation_fragment

import com.develop.zuzik.fragmentnavigation.navigation_fragment.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry

/**
 * User: zuzik
 * Date: 12/25/16
 */
class CompositeListNavigationFragmentFactory : CompositeFragmentFactory {

    override fun create(entries: List<NavigationEntry>): FragmentFactory {
        return ListNavigationFragmentFactory(entries)
    }
}