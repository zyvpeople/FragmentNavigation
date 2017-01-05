package com.develop.zuzik.fragmentnavigation.scene

import com.develop.zuzik.fragmentnavigation.navigation.list.CompositeListNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.CompositeFragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry
import com.develop.zuzik.fragmentnavigation.navigation.pager.CompositePagerNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class MultipleBuilder private constructor(private val tag: String,
                                          private val compositeFragmentFactory: CompositeFragmentFactory) : Builder {

    companion object {
        internal fun createForPager(tag: String) = MultipleBuilder(tag, CompositePagerNavigationFragmentFactory())
        internal fun createForList(tag: String) = MultipleBuilder(tag, CompositeListNavigationFragmentFactory())
    }

    private val builders = mutableListOf<Builder>()

    fun single(tag: String, fragmentFactory: FragmentFactory) {
        builders += SingleBuilder.create(tag, fragmentFactory)
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