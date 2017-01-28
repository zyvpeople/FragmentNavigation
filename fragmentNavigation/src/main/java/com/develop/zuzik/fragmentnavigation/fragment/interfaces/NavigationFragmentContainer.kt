package com.develop.zuzik.fragmentnavigation.fragment.interfaces

import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory
import com.develop.zuzik.fragmentnavigation.scheme.Scheme

/**
 * User: zuzik
 * Date: 1/21/17
 */
interface NavigationFragmentContainer {
    val scheme: Scheme<FragmentFactory>
}