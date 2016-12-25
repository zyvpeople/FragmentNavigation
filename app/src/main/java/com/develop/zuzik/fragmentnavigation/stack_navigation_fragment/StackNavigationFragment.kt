package com.develop.zuzik.fragmentnavigation.stack_navigation_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.PushChildStrategy
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.ReplaceOldWithNewPushChildStrategy

/**
 * User: zuzik
 * Date: 12/22/16
 */

class StackNavigationFragment : Fragment(), NavigationFragment {

    companion object {

        private val KEY_ROOT_FRAGMENT_FACTORY = "KEY_ROOT_FRAGMENT_FACTORY"

        fun create(rootFragmentFactory: FragmentFactory): StackNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_ROOT_FRAGMENT_FACTORY, rootFragmentFactory)
            val fragment = StackNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var rootFragmentFactory: FragmentFactory
    private val pushChildStrategy: PushChildStrategy = ReplaceOldWithNewPushChildStrategy()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootFragmentFactory = arguments.getSerializable(KEY_ROOT_FRAGMENT_FACTORY) as FragmentFactory
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_stack_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hasChild = childFragmentManager.findFragmentById(R.id.placeholder) != null
        if (!hasChild) {
            pushChild(rootFragmentFactory.create())
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

    //endregion
}
