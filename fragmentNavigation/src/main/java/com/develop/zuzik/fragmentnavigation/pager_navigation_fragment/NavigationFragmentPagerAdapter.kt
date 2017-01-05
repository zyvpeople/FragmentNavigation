package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry
import java.util.*

/**
 * User: zuzik
 * Date: 12/24/16
 */

internal class NavigationFragmentPagerAdapter(fragmentManager: FragmentManager,
                                              val entries: MutableList<NavigationEntry>) : FragmentStatePagerAdapter(fragmentManager) {

    val cachedFragmentsAtPositions = SparseArray<Fragment>()
    val removedFragments: MutableList<Fragment> = ArrayList()

    override fun getItem(position: Int): Fragment? {
        return entries[position].factory.create()
    }

    override fun getCount(): Int {
        return entries.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        cachedFragmentsAtPositions.put(position, fragment)
        return fragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        cachedFragmentsAtPositions.remove(position)
        super.destroyItem(container, position, `object`)
    }

    override fun getItemPosition(`object`: Any?): Int {
        return if (removedFragments.contains(`object`)) {
            removedFragments.remove(`object`)
            PagerAdapter.POSITION_NONE
        } else {
            super.getItemPosition(`object`)
        }
    }

    //    fun pushChild(child: Fragment, parent: Fragment) {
//        pushChild(child, parent.parentFragment.tag)
//    }
//
//    fun pushChild(child: Fragment, pageNumber: Int) {
//        pushChild(child, tags[pageNumber])
//    }
//
//    private fun pushChild(child: Fragment, tag: String) {
//        navigationFragment(tag)?.pushChild(child)
//    }
//
//    fun popChild(pageNumber: Int, fail: () -> Unit) {
//        popChild(tags[pageNumber], fail)
//    }
//
//    fun popChild(child: Fragment, fail: () -> Unit) {
//        popChild(child.parentFragment.tag, fail)
//    }
//
//    private fun popChild(tag: String, fail: () -> Unit) {
//        navigationFragment(tag)?.popChild(fail)
//    }
//
//    private fun navigationFragment(tag: String): NavigationFragment? =
//            fragmentManager.findFragmentByTag(tag) as? NavigationFragment
}
