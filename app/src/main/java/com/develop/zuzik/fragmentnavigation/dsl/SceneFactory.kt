package com.develop.zuzik.fragmentnavigation.dsl

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */

class SceneFactory {

    fun single(factory: FragmentFactory) = Scene(factory)

    fun pager(addChildren: MultipleBuilder.() -> Unit) =
            multipleScene(MultipleBuilder.createForPager(""), addChildren)

    fun list(addChildren: MultipleBuilder.() -> Unit) =
            multipleScene(MultipleBuilder.createForList(""), addChildren)

    private fun multipleScene(builder: MultipleBuilder, addChildren: MultipleBuilder.() -> Unit): Scene {
        builder.addChildren()
        return Scene(builder.build().factory)
    }
}
