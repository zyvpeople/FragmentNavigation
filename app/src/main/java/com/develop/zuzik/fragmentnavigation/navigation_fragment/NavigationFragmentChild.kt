package com.develop.zuzik.fragmentnavigation.navigation_fragment

/**
 * User: zuzik
 * Date: 1/4/17
 */
interface NavigationFragmentChild {

    fun onAddedToParentNavigationFragment(parent: NavigationFragment)
    fun onRemovedFromParentNavigationFragment()

}