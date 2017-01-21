package com.develop.zuzik.fragmentnavigation.model.builder

import com.develop.zuzik.fragmentnavigation.model.Node

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ChildBuilder<Value> internal constructor(private val tag: String,
                                               private val value: Value) : Builder<Value> {

    override fun build(): Node<Value> = Node(tag, value, null, mutableListOf())
}