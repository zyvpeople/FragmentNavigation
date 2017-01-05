package com.develop.zuzik.fragmentnavigation.scene_placeholder

import android.support.annotation.IdRes
import android.support.v4.app.FragmentManager
import com.develop.zuzik.fragmentnavigation.dsl.Scene

/**
 * User: zuzik
 * Date: 1/5/17
 */
class ScenePlaceholder(private val scene: Scene) {

    fun showScene(fragmentManager: FragmentManager, @IdRes placeholderResId: Int) {
        val existedFragment = fragmentManager.findFragmentById(placeholderResId)
        if (existedFragment == null) {
            fragmentManager
                    .beginTransaction()
                    .add(placeholderResId, scene.fragmentFactory.create())
                    .commitNow()
        }
    }
}