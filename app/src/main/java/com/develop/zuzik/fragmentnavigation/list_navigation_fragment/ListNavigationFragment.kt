package com.develop.zuzik.fragmentnavigation.list_navigation_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragmentChild
import java.util.*

/**
 * User: zuzik
 * Date: 12/22/16
 */

class ListNavigationFragment : Fragment(), NavigationFragment {

    companion object {

        private val KEY_NAVIGATION_ENTRIES = "KEY_NAVIGATION_ENTRIES"

        fun create(entries: List<NavigationEntry>): ListNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_NAVIGATION_ENTRIES, ArrayList(entries))
            val fragment = ListNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_list_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.placeholder) != null) {
            fragments()
                    .forEach { notifyChildOnAddedToParent(it) }
            return
        }
        val entries = arguments.getSerializable(KEY_NAVIGATION_ENTRIES) as ArrayList<NavigationEntry>
        val indexForNavigation = 0
        val transaction = childFragmentManager.beginTransaction()
        entries
                .forEachIndexed { index, navigationEntry ->
                    val fragment = navigationEntry.factory.create()
                    notifyChildOnAddedToParent(fragment)
                    transaction.add(R.id.placeholder, fragment, navigationEntry.tag)
                    if (index != indexForNavigation) {
                        transaction.detach(fragment)
                    }
                }
        transaction.commitNow()
    }

//region NavigationFragment

    override fun pushChild(child: Fragment) {

    }

    override fun popChild(fail: () -> Unit) {

    }

    override fun navigateToIndex(index: Int) {

    }

//endregion

    override fun addFragment(tag: String, factory: FragmentFactory) {
        if (fragments().find { it.tag == tag } == null) {
            val fragment = factory.create()
            notifyChildOnAddedToParent(fragment)
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.placeholder, fragment, tag)
                    .detach(fragment)
                    .commitNow()
        } else {
            throw RuntimeException("Fragment with tag '$tag' already exit")
        }
    }

    override fun removeFragment(tag: String) {
        if (fragments().find { it.tag == tag } != null) {
            val fragment = childFragmentManager.findFragmentByTag(tag)
            notifyChildOnRemovedFromParent(fragment)
            childFragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow()
        } else {
            throw RuntimeException("Fragment with tag '$tag' does not exit")
        }
    }

    override fun goToFragment(tag: String) {
        if (fragments().find { it.tag == tag } != null) {
            val fragments = fragments()
            val transaction = childFragmentManager.beginTransaction()
            fragments
                    .forEach {
                        if (it.tag == tag) {
                            transaction.attach(it)
                        } else {
                            transaction.detach(it)
                        }
                    }
            transaction.commitNow()
        } else {
            throw RuntimeException("Fragment with tag '$tag' does not exit")
        }
    }

    override fun pushFragment(tag: String, factory: FragmentFactory) {
        addFragment(tag, factory)
        goToFragment(tag)
    }

    override fun popFragment(fail: () -> Unit) {
        val fragments = fragments()
        val oldTopFragment = fragments.getOrNull(fragments.size - 1)
        val newTopFragment = fragments.getOrNull(fragments.size - 2)
        if (oldTopFragment != null && newTopFragment != null) {
            removeFragment(oldTopFragment.tag)
            goToFragment(newTopFragment.tag)
        } else {
            fail()
        }
    }

    private fun fragments(): List<Fragment> = (childFragmentManager.fragments ?: emptyList()).filterNotNull()

    private fun notifyChildOnAddedToParent(fragment: Fragment) {
        (fragment as? NavigationFragmentChild)?.onAddedToParentNavigationFragment(this)
    }

    private fun notifyChildOnRemovedFromParent(fragment: Fragment?) {
        (fragment as? NavigationFragmentChild)?.onRemovedFromParentNavigationFragment()
    }
}
