package com.develop.zuzik.fragmentnavigationsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.develop.zuzik.fragmentnavigation.scene.SceneBuilder
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationFragment
import com.develop.zuzik.fragmentnavigation.scene.ScenePlaceholder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ScenePlaceholder(createScene()).showScene(supportFragmentManager, R.id.placeholder)
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

    private fun createScene() =
            SceneBuilder()
                    .pager {
                        list("0") {
                            single("0a", TextFragmentFactory("0a"))
                        }
                        list("1") {
                            single("1a", TextFragmentFactory("1a"))
                        }
                        single("2a", TextFragmentFactory("2a"))
                    }

    fun navigationFragment() =
            supportFragmentManager.findFragmentById(R.id.placeholder) as? NavigationFragment

    override fun onBackPressed() {
        navigationFragment()?.popFragment { super.onBackPressed() }
    }
}
