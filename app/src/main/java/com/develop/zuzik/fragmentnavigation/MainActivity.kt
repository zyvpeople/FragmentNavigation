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
        navigateToIndex.setOnClickListener {
            val indexForNavigation = Integer.parseInt(navigationIndex.text.toString())
            navigationFragment()?.navigateToIndex(indexForNavigation)
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
                        stack {
                            single(TextFragmentFactory("a1"))
                            single(TextFragmentFactory("a2"))
                        }
                        stack {
                            single(TextFragmentFactory("b1"))
                            single(TextFragmentFactory("b2"))
                        }
                        stack {
                            single(TextFragmentFactory("c1"))
                            single(TextFragmentFactory("c2"))
                        }
                    }
//            Scene()
//                    .pager {
//                        stack {
//                            stack {
//                                single(TextFragmentFactory("aaa1"))
//                                single(TextFragmentFactory("aaa2"))
//                            }
//                            stack {
//                                single(TextFragmentFactory("bbb1"))
//                                single(TextFragmentFactory("bbb2"))
//                            }
//                        }
//                        single(TextFragmentFactory("001"))
//                        stack { single(TextFragmentFactory("10")) }
//                        stack {
//                            single(TextFragmentFactory("20"))
//                            single(TextFragmentFactory("21"))
//                        }
//                        pager {
//                            stack {
//                                single(TextFragmentFactory("300"))
//                                single(TextFragmentFactory("301"))
//                            }
//                        }
//                        pager {
//                            stack {
//                                single(TextFragmentFactory("310"))
//                                single(TextFragmentFactory("311"))
//                            }
//                        }
//                        pager {
//                            stack {
//                                single(TextFragmentFactory("320"))
//                                single(TextFragmentFactory("321"))
//                            }
//                            stack {
//                                single(TextFragmentFactory("aaa1"))
//                                single(TextFragmentFactory("aaa2"))
//                            }
//                            stack {
//                                single(TextFragmentFactory("bbb1"))
//                                single(TextFragmentFactory("bbb2"))
//                            }
//                        }
//                    }

    private fun createStackNavigationFragment() =
            Scene()
                    .stack {
                        single(TextFragmentFactory("0"))
                        single(TextFragmentFactory("1"))
//                        single(TextFragmentFactory("2"))
//                        single(TextFragmentFactory("3"))
//                        pager {
//                            single(TextFragmentFactory("a"))
//                            single(TextFragmentFactory("b"))
//                        }
//                        single(TextFragmentFactory("4"))
                    }

    private fun createTabsNavigationFragment() =
            Scene()
                    .tabs {
                        single(TextFragmentFactory("0"))
                        single(TextFragmentFactory("1"))
                        single(TextFragmentFactory("2"))
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
