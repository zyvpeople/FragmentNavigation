package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 12/24/16
 */
class PagerNavigationFragmentFactory(private val factories: Array<FragmentFactory>) : FragmentFactory {

    override fun create() = PagerNavigationFragment.create(factories)
}