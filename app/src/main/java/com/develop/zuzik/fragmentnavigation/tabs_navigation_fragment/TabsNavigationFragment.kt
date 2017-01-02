package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment

/**
 * User: zuzik
 * Date: 12/22/16
 */

class TabsNavigationFragment : Fragment(), NavigationFragment {

    companion object {

        private val KEY_FACTORIES = "KEY_FACTORIES"
        private val KEY_STATE_CURRENT_INDEX = "KEY_STATE_CURRENT_INDEX"

        fun create(entries: List<NavigationEntry>): TabsNavigationFragment {
            TODO()
            /*
            val bundle = Bundle()
            bundle.putSerializable(KEY_FACTORIES, factories)
            val fragment = TabsNavigationFragment()
            fragment.arguments = bundle
            return fragment
             */
            return TabsNavigationFragment()
        }
    }

    private var currentIndex: Int? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_tabs_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factories = arguments.getSerializable(TabsNavigationFragment.KEY_FACTORIES) as Array<FragmentFactory>
        if (!factories.isEmpty()) {
            navigateToIndex(currentIndex ?: savedInstanceState?.getInt(KEY_STATE_CURRENT_INDEX) ?: 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (currentIndex != null) {
            outState?.putInt(KEY_STATE_CURRENT_INDEX, currentIndex!!)
        }
    }

    //region NavigationFragment

    override fun pushChild(child: Fragment) {
//        childFragmentManager
//                .beginTransaction()
//                .add(R.id.placeholder, child)
//                .commitNow()
//        navigateToIndex(childFragmentManager.fragments.size - 1)
    }

    override fun popChild(fail: () -> Unit) {
//        val fragments = childFragmentManager.fragments
//        val hasMoreThenOneChild = fragments != null && fragments.size > 1
//        if (hasMoreThenOneChild) {
//            childFragmentManager
//                    .beginTransaction()
//                    .remove(fragments[fragments.size - 1])
//                    .commitNow()
//            navigateToIndex(fragments.size - 1)
//        } else {
//            fail()
//        }
    }

    override fun navigateToIndex(index: Int) {
        val transaction = childFragmentManager.beginTransaction()

        val fragmentForNavigation: Fragment? = childFragmentManager.findFragmentByTag(index.toString())
        val fragments = childFragmentManager.fragments ?: emptyList()
        fragments
                .filter { it != fragmentForNavigation }
                .filterNot { it.isDetached }
                .forEach { transaction.detach(it) }

        if (fragmentForNavigation != null) {
            transaction.attach(fragmentForNavigation)
        } else {
            val factories = arguments.getSerializable(TabsNavigationFragment.KEY_FACTORIES) as Array<FragmentFactory>
            val fragment = factories[index].create()
            transaction.add(R.id.placeholder, fragment, index.toString())
        }

        transaction.commitNow()
        currentIndex = index
    }

    //endregion
}
