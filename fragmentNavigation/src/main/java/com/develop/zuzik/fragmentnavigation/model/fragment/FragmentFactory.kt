package com.develop.zuzik.fragmentnavigation.model.fragment

import android.support.v4.app.Fragment
import java.io.Serializable

/**
 * User: zuzik
 * Date: 1/21/17
 */
interface FragmentFactory : Serializable {
    fun create(path: List<String>): Fragment
}