package com.develop.zuzik.fragmentnavigation.dsl

import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry

/**
 * User: zuzik
 * Date: 12/25/16
 */
internal interface Builder {

    fun build(): NavigationEntry
}