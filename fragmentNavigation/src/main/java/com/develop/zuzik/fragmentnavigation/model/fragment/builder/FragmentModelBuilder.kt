package com.develop.zuzik.fragmentnavigation.model.fragment.builder

import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.builder.ModelBuilder
import com.develop.zuzik.fragmentnavigation.model.builder.ParentBuilder
import com.develop.zuzik.fragmentnavigation.model.fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.ListNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.PagerNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 1/22/17
 */
class FragmentModelBuilder : ModelBuilder<FragmentFactory>() {

    fun list(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<FragmentFactory>.() -> Unit): Model<FragmentFactory>
            = parent(tag, ListNavigationFragmentFactory(), currentNodeTag, addChildren)

    fun pager(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<FragmentFactory>.() -> Unit): Model<FragmentFactory>
            = parent(tag, PagerNavigationFragmentFactory(), currentNodeTag, addChildren)
}