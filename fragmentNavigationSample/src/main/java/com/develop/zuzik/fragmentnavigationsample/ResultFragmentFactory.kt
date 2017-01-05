package com.develop.zuzik.fragmentnavigationsample

import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory

/**
 * User: zuzik
 * Date: 1/4/17
 */
class ResultFragmentFactory : FragmentFactory {
    override fun create() = ResultFragment.create()
}