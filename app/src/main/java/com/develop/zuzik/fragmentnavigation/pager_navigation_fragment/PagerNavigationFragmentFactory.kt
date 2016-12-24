package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/24/16
 */
class PagerNavigationFragmentFactory(private val factories: Array<StackNavigationFragmentFactory>) : FragmentFactory {

    override fun create() = PagerNavigationFragment.create(factories)
}