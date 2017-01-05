package com.develop.zuzik.fragmentnavigation.navigation.interfaces

/**
 * User: zuzik
 * Date: 12/24/16
 */
interface NavigationFragment {

    fun addFragment(tag: String, factory: FragmentFactory)
    fun removeFragment(tag: String)
    fun goToFragment(tag: String)
    fun pushFragment(tag: String, factory: FragmentFactory)
    fun popFragment(fail: () -> Unit)
}