package com.develop.zuzik.fragmentnavigation.navigation_fragment_builder

import android.support.v4.app.Fragment
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory

/**
 * User: zuzik
 * Date: 12/25/16
 */
class NavigationFragmentBuilder {

    fun stack(rootFragmentFactory: FragmentFactory): Fragment =
            StackNavigationFragmentFactory(rootFragmentFactory).create()

    fun pager(fillPageBuilder: PagerBuilder.() -> Unit): Fragment {
        val pagerBuilder = PagerBuilder()
        pagerBuilder.fillPageBuilder()
        return pagerBuilder.build().create()
    }
}