package com.develop.zuzik.fragmentnavigation.model.fragment.builder

import com.develop.zuzik.fragmentnavigation.model.builder.ParentBuilder
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelListNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelPagerNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 1/22/17
 */

fun ParentBuilder<ModelFragmentFactory>.list(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<ModelFragmentFactory>.() -> Unit) {
    parent(tag, ModelListNavigationFragmentFactory(), currentNodeTag, addChildren)
}

fun ParentBuilder<ModelFragmentFactory>.pager(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<ModelFragmentFactory>.() -> Unit) {
    parent(tag, ModelPagerNavigationFragmentFactory(), currentNodeTag, addChildren)
}