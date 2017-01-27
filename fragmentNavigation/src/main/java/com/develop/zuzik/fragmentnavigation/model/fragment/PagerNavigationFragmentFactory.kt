package com.develop.zuzik.fragmentnavigation.model.fragment

/**
 * User: zuzik
 * Date: 1/21/17
 */
class PagerNavigationFragmentFactory : FragmentFactory {
    override fun create(path: List<String>) = PagerNavigationFragment.create(path)
}