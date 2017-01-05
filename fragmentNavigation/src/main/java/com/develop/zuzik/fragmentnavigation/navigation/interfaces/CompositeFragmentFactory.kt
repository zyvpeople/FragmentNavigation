package com.develop.zuzik.fragmentnavigation.navigation.interfaces

/**
 * User: zuzik
 * Date: 12/24/16
 */
interface CompositeFragmentFactory {
    fun create(entries: List<NavigationEntry>): FragmentFactory
}