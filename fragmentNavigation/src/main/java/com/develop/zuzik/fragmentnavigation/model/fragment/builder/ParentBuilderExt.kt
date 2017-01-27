package com.develop.zuzik.fragmentnavigation.model.fragment.builder

import com.develop.zuzik.fragmentnavigation.model.builder.ParentBuilder
import com.develop.zuzik.fragmentnavigation.model.fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.NavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.PagerNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 1/22/17
 */

fun ParentBuilder<FragmentFactory>.list(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<FragmentFactory>.() -> Unit) {
    parent(tag, NavigationFragmentFactory(), currentNodeTag, addChildren)
}

fun ParentBuilder<FragmentFactory>.pager(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<FragmentFactory>.() -> Unit) {
    parent(tag, PagerNavigationFragmentFactory(), currentNodeTag, addChildren)
}