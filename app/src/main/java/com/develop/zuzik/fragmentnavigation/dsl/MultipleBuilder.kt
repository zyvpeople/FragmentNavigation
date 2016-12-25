package com.develop.zuzik.fragmentnavigation.dsl

import com.develop.zuzik.fragmentnavigation.navigation_fragment.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.pager_navigation_fragment.CompositePagerNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.pager_navigation_fragment.CompositeStackNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class MultipleBuilder private constructor(private val compositeFragmentFactory: CompositeFragmentFactory) : Builder {

    companion object {
        internal fun createForStack() = MultipleBuilder(CompositeStackNavigationFragmentFactory())
        internal fun createForPager() = MultipleBuilder(CompositePagerNavigationFragmentFactory())
    }

    private val builders = mutableListOf<Builder>()

    fun single(fragmentFactory: FragmentFactory) {
        builders += SingleBuilder.create(fragmentFactory)
    }

    fun stack(addChildren: MultipleBuilder.() -> Unit) {
        val builder = createForStack()
        builder.addChildren()
        builders += builder
    }

    fun pager(addChildren: MultipleBuilder.() -> Unit) {
        val builder = createForPager()
        builder.addChildren()
        builders += builder
    }

    override fun build(): FragmentFactory {
        return compositeFragmentFactory.create(builders.map { it.build() }.toTypedArray())
    }
}