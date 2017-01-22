package com.develop.zuzik.fragmentnavigation.model.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.develop.zuzik.fragmentnavigation.model.Node

/**
 * User: zuzik
 * Date: 12/24/16
 */

internal class ModelNavigationFragmentPagerAdapter(
        fragmentManager: FragmentManager,
        var node: Node<ModelFragmentFactory>?,
        val path: List<String>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int) = node?.children?.getOrNull(position)?.value?.create(path)

    override fun getCount() = node?.children?.size ?: 0

    override fun getItemPosition(fragment: Any?): Int {
        return if (fragment is Fragment && node != null && node!!.children.map { it.tag }.contains(fragment.tag)) {
            super.getItemPosition(fragment)
        } else {
            PagerAdapter.POSITION_NONE
        }
    }
}
