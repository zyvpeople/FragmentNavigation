package com.develop.zuzik.fragmentnavigation.navigation_fragment_builder

import android.support.v4.app.Fragment
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.PushChildStrategy
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.ReplaceOldWithNewPushChildStrategy

/**
 * User: zuzik
 * Date: 12/25/16
 */
class NavigationFragmentBuilder {

    fun stack(rootFragmentFactory: FragmentFactory,
              pushChildStrategy: PushChildStrategy): Fragment =
            StackNavigationFragmentFactory(rootFragmentFactory, pushChildStrategy).create()

    fun pager(fillPageBuilder: PagerBuilder.() -> Unit): Fragment {
        val pagerBuilder = PagerBuilder(ReplaceOldWithNewPushChildStrategy())
        pagerBuilder.fillPageBuilder()
        return pagerBuilder.build().create()
    }
}