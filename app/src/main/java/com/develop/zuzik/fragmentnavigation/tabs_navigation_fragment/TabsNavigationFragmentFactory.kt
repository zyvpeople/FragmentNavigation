package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 12/24/16
 */
class TabsNavigationFragmentFactory(private val factories: Array<FragmentFactory>) : FragmentFactory {

    override fun create() = TabsNavigationFragment.create(factories)
}