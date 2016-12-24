package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragment
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/24/16
 */

internal class NavigationFragmentPagerAdapter(private val fragmentManager: FragmentManager,
                                              private val factories: Array<StackNavigationFragmentFactory>) : FragmentPagerAdapter(fragmentManager) {

    private val tags = SparseArray<String>()

    override fun getItem(position: Int): Fragment? {
        return factories[position].create()
    }

    override fun getCount(): Int {
        return factories.size
    }

    override fun instantiateItem(container: ViewGroup?, position: Int): Any {
        val fragment = super.instantiateItem(container, position) as Fragment
        tags.put(position, fragment.tag)
        return fragment
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        tags.remove(position)
        super.destroyItem(container, position, `object`)
    }

    fun pushChild(child: Fragment, parent: Fragment) {
        pushChild(child, parent.parentFragment.tag)
    }

    fun pushChild(child: Fragment, pageNumber: Int) {
        pushChild(child, tags[pageNumber])
    }

    private fun pushChild(child: Fragment, tag: String) {
        val stackNavigationFragment = fragmentManager.findFragmentByTag(tag) as StackNavigationFragment
        stackNavigationFragment.pushChild(child)
    }

    fun popChild(pageNumber: Int, fail: () -> Unit) {
        popChild(tags[pageNumber], fail)
    }

    fun popChild(child: Fragment, fail: () -> Unit) {
        popChild(child.parentFragment.tag, fail)
    }

    private fun popChild(tag: String, fail: () -> Unit) {
        val container = fragmentManager.findFragmentByTag(tag) as StackNavigationFragment
        container.popChild(fail)
    }
}
