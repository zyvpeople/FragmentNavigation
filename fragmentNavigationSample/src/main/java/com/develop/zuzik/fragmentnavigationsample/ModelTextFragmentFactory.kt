package com.develop.zuzik.fragmentnavigationsample

import com.develop.zuzik.fragmentnavigation.model.fragment.ModelFragmentFactory

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ModelTextFragmentFactory(private val text: String) : ModelFragmentFactory {
    override fun crate() = TextFragment.create(text)
}