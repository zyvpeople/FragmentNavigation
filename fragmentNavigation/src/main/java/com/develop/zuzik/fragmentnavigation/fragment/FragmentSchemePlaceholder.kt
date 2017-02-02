package com.develop.zuzik.fragmentnavigation.fragment

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory
import com.develop.zuzik.fragmentnavigation.scheme.Scheme

/**
 * User: zuzik
 * Date: 1/5/17
 */
class FragmentSchemePlaceholder(private val scheme: Scheme<FragmentFactory>,
                                private val fragmentManager: FragmentManager,
                                @IdRes private val placeholderResId: Int) {

    val fragment: Fragment?
        get() = fragmentManager.findFragmentByTag(scheme.state.tag)

    fun show() {
        val tag = scheme.state.tag
        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentManager
                    .beginTransaction()
                    .add(placeholderResId, scheme.state.value.create(listOf(tag)), tag)
                    .commitNow()
        }
    }
}