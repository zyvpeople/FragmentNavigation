package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment

import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.PushChildStrategy
import java.io.Serializable

/**
 * User: zuzik
 * Date: 12/24/16
 */
class StackNavigationFragmentFactory(private val rootFragmentFactory: FragmentFactory,
                                     private val pushChildStrategy: PushChildStrategy) : Serializable {

    fun create() = StackNavigationFragment.create(rootFragmentFactory, pushChildStrategy)
}