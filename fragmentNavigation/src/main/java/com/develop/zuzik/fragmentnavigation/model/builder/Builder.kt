package com.develop.zuzik.fragmentnavigation.model.builder

import com.develop.zuzik.fragmentnavigation.model.Node

/**
 * User: zuzik
 * Date: 1/21/17
 */
internal interface Builder<Value> {
    fun build(): Node<Value>
}