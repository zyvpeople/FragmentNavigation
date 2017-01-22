package com.develop.zuzik.fragmentnavigation.model.fragment

import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.develop.zuzik.fragmentnavigation.model.Model

/**
 * User: zuzik
 * Date: 1/5/17
 */
class FragmentPlaceholder(private val model: Model<ModelFragmentFactory>,
                          private val fragmentManager: FragmentManager,
                          @IdRes private val placeholderResId: Int) {

    fun show() {
        val existedFragment = fragmentManager.findFragmentById(placeholderResId)
        if (existedFragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(placeholderResId, ModelRootNavigationFragment())
                    .commitNow()
        }
    }
}