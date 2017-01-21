package com.develop.zuzik.fragmentnavigation.model.fragment

import com.develop.zuzik.fragmentnavigation.model.Model

/**
 * User: zuzik
 * Date: 1/21/17
 */
interface ModelNavigationFragment<Value> {
    val model: Model<Value>?
}