package com.develop.zuzik.fragmentnavigation.navigation_fragment

import android.support.v4.app.Fragment
import java.io.Serializable

/**
 * User: zuzik
 * Date: 12/24/16
 */
interface FragmentFactory : Serializable {
    fun create(): Fragment
}