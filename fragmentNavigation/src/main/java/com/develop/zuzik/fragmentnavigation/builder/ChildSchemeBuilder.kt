package com.develop.zuzik.fragmentnavigation.builder

import com.develop.zuzik.fragmentnavigation.scheme.Scheme
import com.develop.zuzik.fragmentnavigation.scheme.Node

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ChildSchemeBuilder<Value> internal constructor(private val scheme: Scheme<Value>) : Builder<Value> {

    override fun build(): Node<Value> = scheme.state
}