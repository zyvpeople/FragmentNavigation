package com.develop.zuzik.fragmentnavigation.navigation_fragment_builder

import com.develop.zuzik.fragmentnavigation.pager_navigation_fragment.PagerNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class PagerBuilder : Builder {

    private val builders = mutableListOf<Builder>()

    fun stack(rootFragmentFactory: FragmentFactory) {
        builders += StackBuilder(rootFragmentFactory)
    }

    fun pager(fillPageBuilder: PagerBuilder.() -> Unit) {
        val pageBuilder = PagerBuilder()
        pageBuilder.fillPageBuilder()
        builders += pageBuilder
    }

    internal fun build(): FragmentFactory {
        val factories = mutableListOf <StackNavigationFragmentFactory>()
        for (builder in builders) {
            when (builder) {
                is StackBuilder -> factories += builder.build()
                is PagerBuilder -> factories += StackNavigationFragmentFactory(builder.build())
                else -> NotImplementedError()
            }
        }
        return PagerNavigationFragmentFactory(factories.toTypedArray())
    }
}