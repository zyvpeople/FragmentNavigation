package com.develop.zuzik.fragmentnavigationsample

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 12/24/16
 */

class TextFragmentFactory(private val text: String) : FragmentFactory {

    override fun create() = TextFragment.create(text)
}
