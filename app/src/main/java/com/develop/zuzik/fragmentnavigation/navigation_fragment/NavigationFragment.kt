package com.develop.zuzik.fragmentnavigation.navigation_fragment

import android.support.v4.app.Fragment

/**
 * User: zuzik
 * Date: 12/24/16
 */
interface NavigationFragment {

    fun pushChild(child: Fragment)
    fun popChild(fail: () -> Unit)
}