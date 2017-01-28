package com.develop.zuzik.fragmentnavigation.fragment

import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory

/**
 * User: zuzik
 * Date: 1/21/17
 */
class PagerNavigationFragmentFactory : FragmentFactory {
    override fun create(path: List<String>) = PagerNavigationFragment.create(path)
}