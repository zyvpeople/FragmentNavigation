package com.develop.zuzik.fragmentnavigationsample

import com.develop.zuzik.fragmentnavigation.model.fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 1/21/17
 */
class TextFragmentFactory(private val text: String) : FragmentFactory {
    override fun create(path: List<String>) = TextFragment.create(text)
}