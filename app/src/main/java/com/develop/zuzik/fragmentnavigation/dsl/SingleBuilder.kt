package com.develop.zuzik.fragmentnavigation.dsl

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class SingleBuilder private constructor(private val fragmentFactory: FragmentFactory) : Builder {

    companion object {
        internal fun create(fragmentFactory: FragmentFactory) = SingleBuilder(fragmentFactory)
    }

    override fun build(): FragmentFactory = fragmentFactory
}