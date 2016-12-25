package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import com.develop.zuzik.fragmentnavigation.navigation_fragment.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class CompositePagerNavigationFragmentFactory : CompositeFragmentFactory {

    override fun create(factories: Array<FragmentFactory>): FragmentFactory {
        return PagerNavigationFragmentFactory(factories)
    }
}