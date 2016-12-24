package com.develop.zuzik.fragmentnavigation

import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 12/24/16
 */

class TextFragmentFactory(private val text: String) : FragmentFactory {

    override fun create() = TextFragment.create(text)
}
