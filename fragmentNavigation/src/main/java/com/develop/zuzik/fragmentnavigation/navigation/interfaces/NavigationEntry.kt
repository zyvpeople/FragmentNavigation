package com.develop.zuzik.fragmentnavigation.navigation.interfaces

import java.io.Serializable

/**
 * User: zuzik
 * Date: 1/2/17
 */
class NavigationEntry(val tag: String, val factory: FragmentFactory) : Serializable