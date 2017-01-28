package com.develop.zuzik.fragmentnavigation.fragment.interfaces

import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory

/**
 * User: zuzik
 * Date: 1/21/17
 */
interface NavigationFragment {
    fun push(tag: String, factory: FragmentFactory)
    fun pop()
}