package com.develop.zuzik.fragmentnavigation.model.fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.model.Node
import java.util.*

/**
 * User: zuzik
 * Date: 12/24/16
 */

internal class NavigationFragmentPagerAdapter(
        fragmentManager: FragmentManager,
        var node: Node<FragmentFactory>?,
        val path: List<String>) : FragmentStatePagerAdapter(fragmentManager) {

    private val cachedFragments = HashMap<String, Fragment>()

    override fun getItem(position: Int) =
            node?.children?.getOrNull(position)?.let { it.value.create(path + it.tag) }

    override fun getCount() = node?.children?.size ?: 0

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        node?.let {
            cachedFragments += it.children[position].tag to fragment
        }
        return fragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, fragment: Any?) {
        tagForFragment(fragment)?.let {
            cachedFragments.remove(it)
        }
        super.destroyItem(container, position, fragment)
    }

    override fun getItemPosition(fragment: Any?): Int {
        val tagForFragment = tagForFragment(fragment)
        val childWithTagExists = node?.children?.firstOrNull { it.tag == tagForFragment } != null
        return if (childWithTagExists) {
            super.getItemPosition(fragment)
        } else {
            PagerAdapter.POSITION_NONE
        }
    }

    private fun tagForFragment(fragment: Any?): String? {
        val childTagForFragment = cachedFragments
                .entries
                .firstOrNull { it.value === fragment }
                ?.key
        return childTagForFragment
    }
}
