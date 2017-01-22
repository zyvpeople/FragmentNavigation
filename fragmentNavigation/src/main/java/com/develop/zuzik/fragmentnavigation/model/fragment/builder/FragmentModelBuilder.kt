package com.develop.zuzik.fragmentnavigation.model.fragment.builder

import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.builder.ModelBuilder
import com.develop.zuzik.fragmentnavigation.model.builder.ParentBuilder
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelListNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelPagerNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 1/22/17
 */
class FragmentModelBuilder : ModelBuilder<ModelFragmentFactory>() {

    fun list(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<ModelFragmentFactory>.() -> Unit): Model<ModelFragmentFactory>
            = parent(tag, ModelListNavigationFragmentFactory(), currentNodeTag, addChildren)

    fun pager(tag: String, currentNodeTag: String?, addChildren: ParentBuilder<ModelFragmentFactory>.() -> Unit): Model<ModelFragmentFactory>
            = parent(tag, ModelPagerNavigationFragmentFactory(), currentNodeTag, addChildren)
}