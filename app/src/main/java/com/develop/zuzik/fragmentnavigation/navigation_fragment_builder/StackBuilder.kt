package com.develop.zuzik.fragmentnavigation.navigation_fragment_builder

import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class StackBuilder(private val rootFragmentFactory: FragmentFactory) : Builder {

    internal fun build() = StackNavigationFragmentFactory(rootFragmentFactory)
}