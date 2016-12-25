package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import kotlinx.android.synthetic.main.fragment_pager_navigation.*

/**
 * User: zuzik
 * Date: 12/24/16
 */
class PagerNavigationFragment : Fragment(), NavigationFragment {

    companion object {

        private val KEY_FACTORIES = "KEY_FACTORIES"

        fun create(factories: Array<FragmentFactory>): PagerNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_FACTORIES, factories)
            val fragment = PagerNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter: NavigationFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factories = arguments.getSerializable(KEY_FACTORIES) as Array<FragmentFactory>
        adapter = NavigationFragmentPagerAdapter(childFragmentManager, factories)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pager_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = adapter
    }

    //region NavigationFragment

    override fun pushChild(child: Fragment) {
        pushChild(child, viewPager.currentItem)
    }

    override fun popChild(fail: () -> Unit) {
        popChild(viewPager.currentItem, fail)
    }

    //endregion

    fun pushChild(child: Fragment, parent: Fragment) {
        adapter.pushChild(child, parent)
    }

    fun pushChild(child: Fragment, pageNumber: Int) {
        adapter.pushChild(child, pageNumber)
    }

    fun popChild(pageNumber: Int, fail: () -> Unit) {
        adapter.popChild(pageNumber, fail)
    }

    fun popChild(child: Fragment, fail: () -> Unit) {
        adapter.popChild(child, fail)
    }
}
