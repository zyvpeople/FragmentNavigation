package com.develop.zuzik.fragmentnavigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.SparseArray
import android.view.ViewGroup

/**
 * User: zuzik
 * Date: 12/24/16
 */

class StackFragmentPagerAdapter(private val fragmentManager: FragmentManager, private val factories: Array<TextFragmentFactory>) : FragmentPagerAdapter(fragmentManager) {

    private val tags = SparseArray<String>()

    override fun getItem(position: Int): Fragment? {
        return ContainerFragment.create(factories[position])
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

    fun pushFragment(fragment: Fragment, position: Int) {
        val container = fragmentManager.findFragmentByTag(tags[position]) as ContainerFragment
        container
                .childFragmentManager
                .beginTransaction()
                .replace(container.placeholderId(), fragment)
                .addToBackStack(null)
                .commit()
    }

    fun pushFragment(fragment: Fragment, above: Fragment) {
        val container = fragmentManager.findFragmentByTag(above.parentFragment.tag) as ContainerFragment
        container
                .childFragmentManager
                .beginTransaction()
                .replace(container.placeholderId(), fragment)
                .addToBackStack(null)
                .commit()
    }

    fun popFragment(position: Int, fail: () -> Unit) {
        val container = fragmentManager.findFragmentByTag(tags[position]) as ContainerFragment
        if (container.childFragmentManager.backStackEntryCount > 1) {
            container.childFragmentManager.popBackStack()
        } else {
            fail()
        }
    }
}
