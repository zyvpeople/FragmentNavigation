package com.develop.zuzik.fragmentnavigation.navigation_fragment_builder

import com.develop.zuzik.fragmentnavigation.pager_navigation_fragment.PagerNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.PushChildStrategy

/**
 * User: zuzik
 * Date: 12/25/16
 */
class PagerBuilder(internal val pushChildStrategy: PushChildStrategy) : Builder {

    private val builders = mutableListOf<Builder>()

    fun stack(rootFragmentFactory: FragmentFactory,
              pushChildStrategy: PushChildStrategy) {
        builders += StackBuilder(rootFragmentFactory, pushChildStrategy)
    }

    fun pager(pushChildStrategy: PushChildStrategy, fillPageBuilder: PagerBuilder.() -> Unit) {
        val pageBuilder = PagerBuilder(pushChildStrategy)
        pageBuilder.fillPageBuilder()
        builders += pageBuilder
    }

    internal fun build(): FragmentFactory {
        val factories = mutableListOf <StackNavigationFragmentFactory>()
        for (builder in builders) {
            when (builder) {
                is StackBuilder -> factories += builder.build()
                is PagerBuilder -> factories += StackNavigationFragmentFactory(builder.build(), builder.pushChildStrategy)
                else -> NotImplementedError()
            }
        }
        return PagerNavigationFragmentFactory(factories.toTypedArray())
    }
}