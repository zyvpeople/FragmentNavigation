package com.develop.zuzik.fragmentnavigation.navigation_fragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
interface CompositeFragmentFactory {
    fun create(entries: List<NavigationEntry>): FragmentFactory
}