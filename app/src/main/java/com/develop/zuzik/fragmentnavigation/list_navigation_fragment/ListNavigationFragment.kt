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
import java.io.Serializable
import java.util.*

/**
 * User: zuzik
 * Date: 12/22/16
 */

class ListNavigationFragment : Fragment(), NavigationFragment {

    private class State(val entries: ArrayList<NavigationEntry>) : Serializable

    companion object {

        private val KEY_ARGUMENT_STATE = "KEY_ARGUMENT_STATE"
        private val KEY_SAVED_STATE = "KEY_SAVED_STATE"

        fun create(entries: List<NavigationEntry>): ListNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_ARGUMENT_STATE, State(ArrayList(entries)))
            val fragment = ListNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit private var state: State

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_tabs_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            state = savedInstanceState.getSerializable(KEY_SAVED_STATE) as State
        } else {
            state = arguments.getSerializable(KEY_ARGUMENT_STATE) as State
            val indexForNavigation = 0
            val transaction = childFragmentManager.beginTransaction()
            state
                    .entries
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
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(KEY_SAVED_STATE, state)
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
        if (state.entries.find { it.tag == tag } == null) {
            state.entries.add(NavigationEntry(tag, factory))
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
        if (state.entries.find { it.tag == tag } != null) {
            state.entries -= state.entries.filter { it.tag == tag }
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
        if (state.entries.find { it.tag == tag } != null) {
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

    override fun popFragment(stackBecameEmpty: () -> Unit) {
        val topFragment = state.entries.lastOrNull()
        if (topFragment != null) {
            removeFragment(topFragment.tag)
            val newTopFragment = state.entries.lastOrNull()
            if (newTopFragment != null) {
                goToFragment(newTopFragment.tag)
            } else {
                stackBecameEmpty()
            }
        } else {
            stackBecameEmpty()
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
