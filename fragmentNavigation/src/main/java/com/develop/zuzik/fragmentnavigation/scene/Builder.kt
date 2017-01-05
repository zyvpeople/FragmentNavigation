package com.develop.zuzik.fragmentnavigation.scene

import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry

/**
 * User: zuzik
 * Date: 12/25/16
 */
internal interface Builder {

    fun build(): NavigationEntry
}