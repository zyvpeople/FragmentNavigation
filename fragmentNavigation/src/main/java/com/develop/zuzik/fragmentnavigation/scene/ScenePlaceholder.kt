package com.develop.zuzik.fragmentnavigation.scene

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * User: zuzik
 * Date: 1/5/17
 */
class ScenePlaceholder(private val scene: Scene,
                       private val fragmentManager: FragmentManager,
                       @IdRes private val placeholderResId: Int) {

    fun showScene() {
        val existedFragment = fragmentManager.findFragmentById(placeholderResId)
        if (existedFragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(placeholderResId, scene.fragmentFactory.create())
                    .commitNow()
        }
    }

    fun rootFragment(): Fragment? = fragmentManager.findFragmentById(placeholderResId)
}