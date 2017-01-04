package com.develop.zuzik.fragmentnavigation.navigation_fragment

import android.support.v4.app.Fragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
interface NavigationFragment {

    fun pushChild(child: Fragment)
    fun popChild(fail: () -> Unit)
    fun navigateToIndex(index: Int)

//    fun transaction(): Transaction
//    fun findFragment(tag: String): Fragment?
//    fun findFragment(tagsPath: Array<String>): Fragment?
//
//    fun push(fragmentFactory: FragmentFactory, tag: String)
//    fun pop(isOnLastFragment: () -> Unit)
//    fun goTo(tag: String)

    fun addFragment(tag: String, factory: FragmentFactory)
    fun removeFragment(tag: String)
    fun goToFragment(tag: String)
    fun pushFragment(tag: String, factory: FragmentFactory)
    fun popFragment(fail: () -> Unit)
}