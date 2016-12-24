package com.develop.zuzik.fragmentnavigation

import android.support.v4.app.Fragment

/**
 * User: zuzik
 * Date: 12/22/16
 */
interface NavigationContainer {
    fun pushChild(child: Fragment)
}