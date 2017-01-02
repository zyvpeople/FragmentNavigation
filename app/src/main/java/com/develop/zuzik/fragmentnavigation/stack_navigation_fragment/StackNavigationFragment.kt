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
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.PushChildStrategy
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.ReplaceOldWithNewPushChildStrategy

/**
 * User: zuzik
 * Date: 12/22/16
 */

class StackNavigationFragment : Fragment(), NavigationFragment {

    companion object {

        private val KEY_FACTORIES = "KEY_FACTORIES"

        fun create(entries: List<NavigationEntry>): StackNavigationFragment {
//            val bundle = Bundle()
//            bundle.putSerializable(KEY_FACTORIES, factories)
//            val fragment = StackNavigationFragment()
//            fragment.arguments = bundle
//            return fragment
            return StackNavigationFragment()
        }
    }

    private val pushChildStrategy: PushChildStrategy = ReplaceOldWithNewPushChildStrategy()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_stack_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hasChild = childFragmentManager.findFragmentById(R.id.placeholder) != null
        if (!hasChild) {
            val factories = arguments.getSerializable(StackNavigationFragment.KEY_FACTORIES) as Array<FragmentFactory>
            factories.forEach { pushChild(it.create()) }
        }
    }

    //region NavigationFragment

    override fun pushChild(child: Fragment) {
        pushChildStrategy.pushChild(this, R.id.placeholder, child)
    }

    override fun popChild(fail: () -> Unit) {
        val hasMoreThenOneChildInStack = childFragmentManager.backStackEntryCount > 1
        if (hasMoreThenOneChildInStack) {
            childFragmentManager.popBackStack()
        } else {
            fail()
        }
    }

    override fun navigateToIndex(index: Int) {
        val firstIndexToDelete = index + 1
        val lastIndexToDelete = childFragmentManager.backStackEntryCount - 1
        (firstIndexToDelete..lastIndexToDelete).forEach {
            childFragmentManager.popBackStack()
        }
    }

    //endregion
}
