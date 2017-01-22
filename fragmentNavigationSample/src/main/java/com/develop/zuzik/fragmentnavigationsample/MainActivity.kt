package com.develop.zuzik.fragmentnavigationsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.ModelListener
import com.develop.zuzik.fragmentnavigation.model.Node
import com.develop.zuzik.fragmentnavigation.model.fragment.FragmentPlaceholder
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelFragmentFactory
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelNavigationFragmentContainer
import com.develop.zuzik.fragmentnavigation.model.fragment.ModelRootNavigationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ModelNavigationFragmentContainer {

    private data class FullPath(val path: List<String>, val tag: String)

    override val model: Model<ModelFragmentFactory>
        get() = app.model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentPlaceholder(model, supportFragmentManager, R.id.placeholder).show()

        goTo.setOnClickListener {
            fullPath()?.let {
                model.goTo(it.tag, it.path)
            }
        }
        add.setOnClickListener {
            fullPath()?.let {
                model.add(Node(it.tag, ModelTextFragmentFactory(it.tag), null, mutableListOf()), it.path)
            }
        }
        remove.setOnClickListener {
            fullPath()?.let {
                model.remove(it.tag, it.path)
            }
        }
    }

    private fun fullPath(): FullPath? {
        try {
            val fullPath = fullPath.text.toString().split(":")
            val tag = fullPath.last()
            val path = fullPath.slice(0..fullPath.size - 2)
            return FullPath(path, tag)
        } catch (e: Exception) {
            return null
        }
    }

    override fun onStart() {
        super.onStart()
        model.addListener(listener)
        update(model.state)
    }

    override fun onStop() {
        model.removeListener(listener)
        super.onStop()
    }

    private fun update(node: Node<ModelFragmentFactory>) {
        path.text = currentNodePath("", node)
    }

    private fun currentNodePath(path: String, node: Node<ModelFragmentFactory>): String {
        val newPath = "$path:${node.tag}"
        val currentNode = node.children.firstOrNull { it.tag == node.currentChildTag }
        return if (currentNode != null) {
            currentNodePath(newPath, currentNode)
        } else {
            newPath
        }
    }

    val listener: ModelListener<ModelFragmentFactory> = object : ModelListener<ModelFragmentFactory> {
        override fun invoke(state: Node<ModelFragmentFactory>) {
            update(state)
        }
    }

    //    override fun onStart() {
//        super.onStart()
//        navigationFragment()?.goToFragment("list1")
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        Log.d("MainActivity", "onActivityResult")
//    }
//
//    private fun createScene() =
//            SceneBuilder()
//                    .pager {
//                        single("single0", TextFragmentFactory("single0"))
//                        list("list1") {
//                            single("list1:single0", TextFragmentFactory("list1:single0"))
//                        }
//                        list("list2") {
//                            single("list2:single0", TextFragmentFactory("list2:single0"))
//                        }
//                        pager("pager3") {
//                            single("pager3:single0", TextFragmentFactory("pager3:single0"))
//                            single("pager3:single1", TextFragmentFactory("pager3:single1"))
//                            list("pager3:list2") {
//                                single("pager3:list2:single0", TextFragmentFactory("pager3:list2:single0"))
//                            }
//                        }
//                    }
//
//    override fun onBackPressed() {
//        navigationFragment()?.popFragment { super.onBackPressed() }
//    }
//
//    fun navigationFragment() =
//            scenePlaceholder.rootFragment() as? NavigationFragment
}
