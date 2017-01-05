package com.develop.zuzik.fragmentnavigation.dsl

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry

/**
 * User: zuzik
 * Date: 12/25/16
 */
class SingleBuilder private constructor(private val entry: NavigationEntry) : Builder {

    companion object {
        internal fun create(tag: String, fragmentFactory: FragmentFactory) = SingleBuilder(NavigationEntry(tag, fragmentFactory))
    }

    override fun build(): NavigationEntry = entry
}