package com.develop.zuzik.fragmentnavigation.navigation.list

import com.develop.zuzik.fragmentnavigation.navigation.interfaces.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry

/**
 * User: zuzik
 * Date: 12/25/16
 */
class CompositeListNavigationFragmentFactory : CompositeFragmentFactory {

    override fun create(entries: List<NavigationEntry>): FragmentFactory {
        return ListNavigationFragmentFactory(entries)
    }
}