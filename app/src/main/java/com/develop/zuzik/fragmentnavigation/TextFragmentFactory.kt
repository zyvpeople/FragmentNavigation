package com.develop.zuzik.fragmentnavigation

import java.io.Serializable

/**
 * User: zuzik
 * Date: 12/24/16
 */

class TextFragmentFactory(private val text: String) : Serializable {

    fun create() = TextFragment.create(text)
}
