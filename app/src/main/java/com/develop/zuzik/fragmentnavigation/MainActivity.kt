package com.develop.zuzik.fragmentnavigation

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.develop.zuzik.fragmentnavigation.dsl.Scene
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationContainer {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val existedNavigationFragment = supportFragmentManager.findFragmentById(R.id.placeholder)
        if (existedNavigationFragment == null) {
//            val navigationFragment = createPagerNavigationFragment()
//            val navigationFragment = createStackNavigationFragment()
            val navigationFragment = createTabsNavigationFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.placeholder, navigationFragment)
                    .commitNow()
        }
        goToTag.setOnClickListener {
            navigationFragment()?.goToFragment(tagOfFragment.text.toString())
        }
        addWithTag.setOnClickListener {
            navigationFragment()?.addFragment(tagOfFragment.text.toString(), TextFragmentFactory(tagOfFragment.text.toString()))
        }
        removeWithTag.setOnClickListener {
            navigationFragment()?.removeFragment(tagOfFragment.text.toString())
        }
        pushWithTag.setOnClickListener {
            navigationFragment()?.pushFragment(tagOfFragment.text.toString(), TextFragmentFactory(tagOfFragment.text.toString()))
        }
        pop.setOnClickListener {
            navigationFragment()?.popFragment { onBackPressed() }
        }
        //fixme - fragment is not created at this moment
//        navigationFragment()?.navigateToIndex(0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MainActivity", "onActivityResult")
    }

    private fun createPagerNavigationFragment() =
            Scene()
                    .pager {
                        stack("stack1") {
                            single("a1", TextFragmentFactory("a1"))
                            single("a2", TextFragmentFactory("a2"))
                        }
                        stack("stack2") {
                            single("b1", TextFragmentFactory("b1"))
                            single("b2", TextFragmentFactory("b2"))
                        }
                        stack("stack3") {
                            single("c1", TextFragmentFactory("c1"))
                            single("c2", TextFragmentFactory("c2"))
                        }
                    }

    private fun createStackNavigationFragment() =
            Scene()
                    .stack {
                        single("0", TextFragmentFactory("0"))
                        single("1", TextFragmentFactory("1"))
                    }

    private fun createTabsNavigationFragment() =
            Scene()
                    .list {
                        list("0") {
                            single("0a", TextFragmentFactory("0a"))
                        }
                        list("1") {
                            single("1a", TextFragmentFactory("1a"))
                        }
                        list("2") {
                            single("2a", TextFragmentFactory("2a"))
                        }
                    }

    fun navigationFragment() =
            supportFragmentManager.findFragmentById(R.id.placeholder) as? NavigationFragment

    override fun onBackPressed() {
        navigationFragment()?.popFragment { super.onBackPressed() }
    }

    //region NavigationContainer

    override fun pushChild(child: Fragment) {
        navigationFragment()?.pushChild(child)
    }

    //endregion
}
