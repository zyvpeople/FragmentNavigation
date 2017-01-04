package com.develop.zuzik.fragmentnavigation.dsl

import com.develop.zuzik.fragmentnavigation.navigation_fragment.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry
import com.develop.zuzik.fragmentnavigation.pager_navigation_fragment.CompositePagerNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.pager_navigation_fragment.CompositeStackNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.list_navigation_fragment.CompositeListNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class MultipleBuilder private constructor(private val tag: String,
                                          private val compositeFragmentFactory: CompositeFragmentFactory) : Builder {

    companion object {
        internal fun createForStack(tag: String) = MultipleBuilder(tag, CompositeStackNavigationFragmentFactory())
        internal fun createForPager(tag: String) = MultipleBuilder(tag, CompositePagerNavigationFragmentFactory())
        internal fun createForList(tag: String) = MultipleBuilder(tag, CompositeListNavigationFragmentFactory())
    }

    private val builders = mutableListOf<Builder>()

    fun single(tag: String, fragmentFactory: FragmentFactory) {
        builders += SingleBuilder.create(tag, fragmentFactory)
    }

    fun stack(tag: String, addChildren: MultipleBuilder.() -> Unit) {
        val builder = createForStack(tag)
        builder.addChildren()
        builders += builder
    }

    fun pager(tag: String, addChildren: MultipleBuilder.() -> Unit) {
        val builder = createForPager(tag)
        builder.addChildren()
        builders += builder
    }

    fun list(tag: String, addChildren: MultipleBuilder.() -> Unit) {
        val builder = createForList(tag)
        builder.addChildren()
        builders += builder
    }

    override fun build(): NavigationEntry =
            NavigationEntry(tag, compositeFragmentFactory.create(builders.map { it.build() }))

}