package com.develop.zuzik.fragmentnavigation.fragment.builder

import com.develop.zuzik.fragmentnavigation.scheme.Scheme
import com.develop.zuzik.fragmentnavigation.builder.SchemeBuilder
import com.develop.zuzik.fragmentnavigation.builder.ParentBuilder
import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory
import com.develop.zuzik.fragmentnavigation.fragment.ListNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.fragment.PagerNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 1/22/17
 */
class FragmentSchemeBuilder : SchemeBuilder<FragmentFactory>() {

    fun list(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<FragmentFactory>.() -> Unit): Scheme<FragmentFactory>
            = parent(tag, ListNavigationFragmentFactory(), currentNodeTag, addChildren)

    fun pager(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<FragmentFactory>.() -> Unit): Scheme<FragmentFactory>
            = parent(tag, PagerNavigationFragmentFactory(), currentNodeTag, addChildren)
}