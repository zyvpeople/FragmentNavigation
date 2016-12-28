package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import com.develop.zuzik.fragmentnavigation.navigation_fragment.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.TabsNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class CompositeTabsNavigationFragmentFactory : CompositeFragmentFactory {

    override fun create(factories: Array<FragmentFactory>): FragmentFactory {
        return TabsNavigationFragmentFactory(factories)
    }
}