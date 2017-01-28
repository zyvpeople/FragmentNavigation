package com.develop.zuzik.fragmentnavigation.model.fragment.model

import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.Node
import com.develop.zuzik.fragmentnavigation.model.fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 1/28/17
 */

//TODO: create FragmentModel<FragmentFactory> with this method and show method from placeholder
fun Model<FragmentFactory>.add(tag: String, factory: FragmentFactory, path: List<String>) {
    add(Node(tag, factory, null, emptyList()), path)
}