package com.develop.zuzik.fragmentnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
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
            val navigationFragment = createStackNavigationFragment()
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.placeholder, navigationFragment)
                    .commit()
        }
        navigateToIndex.setOnClickListener {
            val indexForNavigation = Integer.parseInt(navigationIndex.text.toString())
            navigationFragment()?.navigateToIndex(indexForNavigation)
        }
    }

    private fun createPagerNavigationFragment() =
            Scene()
                    .pager {
                        single(TextFragmentFactory("00"))
                        stack { single(TextFragmentFactory("10")) }
                        stack {
                            single(TextFragmentFactory("20"))
                            single(TextFragmentFactory("21"))
                        }
                        pager {
                            stack {
                                single(TextFragmentFactory("300"))
                                single(TextFragmentFactory("301"))
                            }
                        }
                        pager {
                            stack {
                                single(TextFragmentFactory("310"))
                                single(TextFragmentFactory("311"))
                            }
                        }
                        pager {
                            stack {
                                single(TextFragmentFactory("320"))
                                single(TextFragmentFactory("321"))
                            }
                        }
                    }

    private fun createStackNavigationFragment() =
            Scene()
                    .stack {
                        single(TextFragmentFactory("0"))
                        single(TextFragmentFactory("1"))
                        single(TextFragmentFactory("2"))
                        single(TextFragmentFactory("3"))
                        single(TextFragmentFactory("4"))
                    }

    fun navigationFragment() =
            supportFragmentManager.findFragmentById(R.id.placeholder) as? NavigationFragment

    override fun onBackPressed() {
        navigationFragment()?.popChild() {
            super.onBackPressed()
        }
    }

    //region NavigationContainer

    override fun pushChild(child: Fragment) {
        navigationFragment()?.pushChild(child)
    }

    //endregion
}
