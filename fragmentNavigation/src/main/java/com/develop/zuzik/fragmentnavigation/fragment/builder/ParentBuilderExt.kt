package com.develop.zuzik.fragmentnavigation.fragment.builder

import com.develop.zuzik.fragmentnavigation.builder.ParentBuilder
import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory
import com.develop.zuzik.fragmentnavigation.fragment.ListNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.fragment.PagerNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 1/22/17
 */

fun ParentBuilder<FragmentFactory>.list(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<FragmentFactory>.() -> Unit) {
    parent(tag, ListNavigationFragmentFactory(), currentNodeTag, addChildren)
}

fun ParentBuilder<FragmentFactory>.pager(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<FragmentFactory>.() -> Unit) {
    parent(tag, PagerNavigationFragmentFactory(), currentNodeTag, addChildren)
}