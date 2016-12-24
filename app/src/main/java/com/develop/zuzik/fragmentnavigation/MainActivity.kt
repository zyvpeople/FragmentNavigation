package com.develop.zuzik.fragmentnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.develop.zuzik.fragmentnavigation.push_strategy.AddDetachPushStrategy
import com.develop.zuzik.fragmentnavigation.push_strategy.ReplacePushStrategy
import kotlinx.android.synthetic.main.activity_main_pager.*

class MainActivity : AppCompatActivity(), NavigationContainer {

    lateinit var adapter: StackFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pager)
        adapter = StackFragmentPagerAdapter(supportFragmentManager, ReplacePushStrategy(), Array(3, { TextFragmentFactory("${it}0") }))
        viewPager.adapter = adapter

//        setContentView(R.layout.activity_main)
//        if (supportFragmentManager.findFragmentById(R.id.placeholder) != null) {
//            return
//        }
//        supportFragmentManager
//                .beginTransaction()
//                .add(R.id.placeholder, ContainerFragment.create())
//                .commitNow()
//        addFragment(TextFragment.create("1"))
//        addFragment(TextFragment.create("2"))
    }

    override fun onStart() {
        super.onStart()
//        adapter.pushFragment(TextFragmentFactory("00").create(), 0)
//        adapter.pushFragment(TextFragmentFactory("10").create(), 1)
//        adapter.pushFragment(TextFragmentFactory("20").create(), 2)
    }

    override fun addFragmentAboveChild(above: Fragment, what: Fragment) {
        adapter.pushFragment(what, above)

//        addFragment(what)
    }

    fun addFragment(what: Fragment) {
        val container = supportFragmentManager.findFragmentById(R.id.placeholder) as ContainerFragment

        val transaction = container
                .childFragmentManager
                .beginTransaction()

        val fragmentsCount = container.childFragmentManager.backStackEntryCount
        val topFragment = if (fragmentsCount > 0) {
            container.childFragmentManager.fragments[fragmentsCount - 1]
        } else {
            null
        }

//        if (topFragment != null) {
//            transaction.hide(topFragment)
//        }

        transaction
                .replace(container.placeholderId(), what)
                .addToBackStack(null)
                .commit()
    }

    override fun onBackPressed() {
        adapter.popFragment(viewPager.currentItem) {
            super.onBackPressed()
        }
//        val container = supportFragmentManager.findFragmentById(R.id.placeholder) as? ContainerFragment
//        if (container != null && container.childFragmentManager.backStackEntryCount > 1) {
//            container.childFragmentManager.popBackStack()
//        } else {
//            super.onBackPressed()
//        }
    }
}
