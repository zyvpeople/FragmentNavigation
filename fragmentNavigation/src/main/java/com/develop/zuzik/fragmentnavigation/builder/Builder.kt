package com.develop.zuzik.fragmentnavigation.builder

import com.develop.zuzik.fragmentnavigation.scheme.Node

/**
 * User: zuzik
 * Date: 1/21/17
 */
internal interface Builder<Value> {
    fun build(): Node<Value>
}