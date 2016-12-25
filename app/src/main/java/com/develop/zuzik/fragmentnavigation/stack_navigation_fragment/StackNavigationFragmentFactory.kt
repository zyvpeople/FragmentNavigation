package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment

import java.io.Serializable

/**
 * User: zuzik
 * Date: 12/24/16
 */
class StackNavigationFragmentFactory(private val rootFragmentFactory: FragmentFactory) : Serializable {

    fun create() = StackNavigationFragment.create(rootFragmentFactory)
}