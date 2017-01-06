package com.develop.zuzik.fragmentnavigationsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationFragment
import com.develop.zuzik.fragmentnavigation.scene.SceneBuilder
import com.develop.zuzik.fragmentnavigation.scene.ScenePlaceholder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var scenePlaceholder: ScenePlaceholder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scenePlaceholder = ScenePlaceholder(createScene(), supportFragmentManager, R.id.placeholder)
        scenePlaceholder.showScene()
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
    }

    override fun onStart() {
        super.onStart()
        navigationFragment()?.goToFragment("list1")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MainActivity", "onActivityResult")
    }

    private fun createScene() =
            SceneBuilder()
                    .pager {
                        single("single0", TextFragmentFactory("single0"))
                        list("list1") {
                            single("list1:single0", TextFragmentFactory("list1:single0"))
                        }
                        list("list2") {
                            single("list2:single0", TextFragmentFactory("list2:single0"))
                        }
                        pager("pager3") {
                            single("pager3:single0", TextFragmentFactory("pager3:single0"))
                            single("pager3:single1", TextFragmentFactory("pager3:single1"))
                            list("pager3:list2") {
                                single("pager3:list2:single0", TextFragmentFactory("pager3:list2:single0"))
                            }
                        }
                    }

    override fun onBackPressed() {
        navigationFragment()?.popFragment { super.onBackPressed() }
    }

    fun navigationFragment() =
            scenePlaceholder.rootFragment() as? NavigationFragment
}
