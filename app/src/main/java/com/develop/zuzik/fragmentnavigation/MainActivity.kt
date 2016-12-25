package com.develop.zuzik.fragmentnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment
import com.develop.zuzik.fragmentnavigation.navigation_fragment_builder.NavigationFragmentBuilder

class MainActivity : AppCompatActivity(), NavigationContainer {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val existedNavigationFragment = supportFragmentManager.findFragmentById(R.id.placeholder)
        if (existedNavigationFragment == null) {
            val navigationFragment = createPagerNavigationFragment()
//            val navigationFragment = createStackNavigationFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.placeholder, navigationFragment)
                    .commit()
        }
    }

    private fun createPagerNavigationFragment() =
            NavigationFragmentBuilder()
                    .pager {
                        stack(TextFragmentFactory("00"))
                        stack(TextFragmentFactory("10"))
                        stack(TextFragmentFactory("20"))
                        stack(TextFragmentFactory("30"))
                        pager() {
                            stack(TextFragmentFactory("400"))
                            stack(TextFragmentFactory("410"))
                            stack(TextFragmentFactory("420"))
                            stack(TextFragmentFactory("430"))
                        }
                    }

    private fun createStackNavigationFragment() = NavigationFragmentBuilder().stack(TextFragmentFactory("0"))

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
