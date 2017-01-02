package com.develop.zuzik.fragmentnavigation.dsl

import android.support.v4.app.Fragment
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */

class Scene {

    fun single(factory: FragmentFactory) = factory.create()

    fun stack(addChildren: MultipleBuilder.() -> Unit) =
            multipleScene(MultipleBuilder.createForStack(""), addChildren)

    fun pager(addChildren: MultipleBuilder.() -> Unit) =
            multipleScene(MultipleBuilder.createForPager(""), addChildren)

    fun tabs(addChildren: MultipleBuilder.() -> Unit) =
            multipleScene(MultipleBuilder.createForTabs(""), addChildren)

    private fun multipleScene(builder: MultipleBuilder, addChildren: MultipleBuilder.() -> Unit): Fragment {
        builder.addChildren()
        return builder.build().factory.create()
    }
}
