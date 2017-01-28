package com.develop.zuzik.fragmentnavigation.fragment.scheme

import com.develop.zuzik.fragmentnavigation.scheme.Scheme
import com.develop.zuzik.fragmentnavigation.scheme.Node
import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory

/**
 * User: zuzik
 * Date: 1/28/17
 */

//TODO: create FragmentModel<FragmentFactory> with this method and show method from placeholder
fun Scheme<FragmentFactory>.add(tag: String, factory: FragmentFactory, path: List<String>) {
    add(Node(tag, factory, null, emptyList()), path)
}