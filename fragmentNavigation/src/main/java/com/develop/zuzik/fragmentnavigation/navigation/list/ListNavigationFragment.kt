package com.develop.zuzik.fragmentnavigation.navigation.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.exception.AttemptToUseNavigationFragmentWhenItIsNotCreatedException
import com.develop.zuzik.fragmentnavigation.exception.FragmentAlreadyExistException
import com.develop.zuzik.fragmentnavigation.exception.FragmentDoesNotExistException
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationFragment
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

    private var viewCreated = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_list_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.placeholder) != null) {
            return
        }
        val entries = arguments.getSerializable(KEY_NAVIGATION_ENTRIES) as ArrayList<NavigationEntry>
        val indexForNavigation = 0
        val transaction = childFragmentManager.beginTransaction()
        entries
                .forEachIndexed { index, navigationEntry ->
                    val fragment = navigationEntry.factory.create()
                    transaction.add(R.id.placeholder, fragment, navigationEntry.tag)
                    if (index != indexForNavigation) {
                        transaction.detach(fragment)
                    }
                }
        transaction.commitNow()
        viewCreated = true
    }

    private fun checkIfViewCreated() {
        if (!viewCreated) {
            throw AttemptToUseNavigationFragmentWhenItIsNotCreatedException()
        }
    }

    //region NavigationFragment

    override fun addFragment(tag: String, factory: FragmentFactory) {
        checkIfViewCreated()
        if (fragments().find { it.tag == tag } == null) {
            val fragment = factory.create()
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.placeholder, fragment, tag)
                    .detach(fragment)
                    .commitNow()
        } else {
            throw FragmentAlreadyExistException(tag)
        }
    }

    override fun removeFragment(tag: String) {
        checkIfViewCreated()
        if (fragments().find { it.tag == tag } != null) {
            val fragment = childFragmentManager.findFragmentByTag(tag)
            childFragmentManager
                    .beginTransaction()
                    .remove(fragment)
                    .commitNow()
        } else {
            throw FragmentDoesNotExistException(tag)
        }
    }

    override fun goToFragment(tag: String) {
        checkIfViewCreated()
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
            throw FragmentDoesNotExistException(tag)
        }
    }

    override fun pushFragment(tag: String, factory: FragmentFactory) {
        checkIfViewCreated()
        addFragment(tag, factory)
        goToFragment(tag)
    }

    override fun popFragment(fail: () -> Unit) {
        checkIfViewCreated()
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

    //endregion
}
