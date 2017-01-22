package com.develop.zuzik.fragmentnavigation.model.builder

import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.Node

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ChildModelBuilder<Value> internal constructor(private val model: Model<Value>) : Builder<Value> {

    override fun build(): Node<Value> = model.state
}