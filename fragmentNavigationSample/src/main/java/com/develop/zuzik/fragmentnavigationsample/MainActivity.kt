package com.develop.zuzik.fragmentnavigationsample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.develop.zuzik.fragmentnavigation.scheme.Scheme
import com.develop.zuzik.fragmentnavigation.scheme.SchemeListener
import com.develop.zuzik.fragmentnavigation.scheme.Node
import com.develop.zuzik.fragmentnavigation.fragment.FragmentSchemePlaceholder
import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory
import com.develop.zuzik.fragmentnavigation.fragment.interfaces.NavigationFragmentContainer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationFragmentContainer {

    private data class FullPath(val path: List<String>, val tag: String)

    override val scheme: Scheme<FragmentFactory>
        get() = app.scheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FragmentSchemePlaceholder(scheme, supportFragmentManager, R.id.placeholder).show()

        goTo.setOnClickListener {
            fullPath()?.let {
                scheme.goTo(it.tag, it.path)
            }
        }
        add.setOnClickListener {
            fullPath()?.let {
                scheme.add(Node(it.tag, TextFragmentFactory(it.tag), null, mutableListOf()), it.path)
            }
        }
        remove.setOnClickListener {
            fullPath()?.let {
                scheme.remove(it.tag, it.path)
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
        scheme.addListener(listener)
        update(scheme.state)
    }

    override fun onStop() {
        scheme.removeListener(listener)
        super.onStop()
    }

    private fun update(node: Node<FragmentFactory>) {
        path.text = currentNodePath("", node)
    }

    private fun currentNodePath(path: String, node: Node<FragmentFactory>): String {
        val newPath = "$path:${node.tag}"
        val currentNode = node.children.firstOrNull { it.tag == node.currentChildTag }
        return if (currentNode != null) {
            currentNodePath(newPath, currentNode)
        } else {
            newPath
        }
    }

    val listener: SchemeListener<FragmentFactory> = object : SchemeListener<FragmentFactory> {
        override fun invoke(state: Node<FragmentFactory>) {
            update(state)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MainActivity", "onActivityResult")
    }

    //TODO: implement back button
//    override fun onBackPressed() {
//        navigationFragment()?.popFragment { super.onBackPressed() }
//    }
}
