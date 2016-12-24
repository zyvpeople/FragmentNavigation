package com.develop.zuzik.fragmentnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment
import com.develop.zuzik.fragmentnavigation.pager_navigation_fragment.PagerNavigationFragment
import com.develop.zuzik.fragmentnavigation.pager_navigation_fragment.PagerNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragment
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.StackNavigationFragmentFactory
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.AddNewDetachOldPushChildStrategy
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.AddNewHideOldPushChildStrategy
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.AddNewPushChildStrategy
import com.develop.zuzik.fragmentnavigation.stack_navigation_fragment.push_strategy.ReplaceOldWithNewPushChildStrategy

class MainActivity : AppCompatActivity(), NavigationContainer {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val existedNavigationFragment = supportFragmentManager.findFragmentById(R.id.placeholder)
        if (existedNavigationFragment == null) {
//            val navigationFragment = createPagerNavigationFragment()
            val navigationFragment = createStackNavigationFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.placeholder, navigationFragment)
                    .commit()
        }
    }

    private fun createPagerNavigationFragment() =
            PagerNavigationFragment
                    .create(
                            arrayOf(
                                    StackNavigationFragmentFactory(TextFragmentFactory("00"), ReplaceOldWithNewPushChildStrategy()),
                                    StackNavigationFragmentFactory(TextFragmentFactory("10"), AddNewPushChildStrategy()),
                                    StackNavigationFragmentFactory(TextFragmentFactory("20"), AddNewDetachOldPushChildStrategy()),
                                    StackNavigationFragmentFactory(TextFragmentFactory("30"), AddNewHideOldPushChildStrategy()),
                                    StackNavigationFragmentFactory(PagerNavigationFragmentFactory(arrayOf(
                                            StackNavigationFragmentFactory(TextFragmentFactory("400"), ReplaceOldWithNewPushChildStrategy()),
                                            StackNavigationFragmentFactory(TextFragmentFactory("410"), AddNewPushChildStrategy()),
                                            StackNavigationFragmentFactory(TextFragmentFactory("420"), AddNewDetachOldPushChildStrategy()),
                                            StackNavigationFragmentFactory(TextFragmentFactory("430"), AddNewHideOldPushChildStrategy()))), AddNewHideOldPushChildStrategy())))

    private fun createStackNavigationFragment() =
            StackNavigationFragment
                    .create(TextFragmentFactory("0"), ReplaceOldWithNewPushChildStrategy())


    fun navigationFragment() =
            supportFragmentManager.findFragmentById(R.id.placeholder) as NavigationFragment

    override fun onBackPressed() {
        navigationFragment().popChild() {
            super.onBackPressed()
        }
    }

    //region NavigationContainer

    override fun pushChild(child: Fragment) {
        navigationFragment().pushChild(child)
    }

    //endregion
}
