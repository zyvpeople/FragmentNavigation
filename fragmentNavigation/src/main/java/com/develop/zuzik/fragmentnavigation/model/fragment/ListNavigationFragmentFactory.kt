package com.develop.zuzik.fragmentnavigation.model.fragment

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ListNavigationFragmentFactory : FragmentFactory {
    override fun create(path: List<String>) = ListNavigationFragment.create(path)
}