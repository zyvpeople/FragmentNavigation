package com.develop.zuzik.fragmentnavigation.scene

import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry

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