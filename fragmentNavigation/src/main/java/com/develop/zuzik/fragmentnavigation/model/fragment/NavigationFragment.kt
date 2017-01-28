package com.develop.zuzik.fragmentnavigation.model.fragment

/**
 * User: zuzik
 * Date: 1/21/17
 */
interface NavigationFragment {
    fun push(tag: String, factory: FragmentFactory)
    fun pop()
}