package com.develop.zuzik.fragmentnavigation.model.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.exception.InterfaceNotImplementedException
import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.ModelListener
import com.develop.zuzik.fragmentnavigation.model.Node
import com.develop.zuzik.fragmentnavigation.model.fragment.model.add
import java.util.*

/**
 * User: zuzik
 * Date: 12/22/16
 */

class ListNavigationFragment : Fragment(), NavigationFragment {

    companion object {

        private val KEY_PATH = "KEY_PATH"

        fun create(path: List<String>): ListNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_PATH, ArrayList(path))
            val fragment = ListNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var path: List<String> = emptyList()
    private var lastSavedState: State? = null
    private var container: NavigationFragmentContainer? = null

    private val model: Model<FragmentFactory>?
        get() = container?.model

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        container = context as? NavigationFragmentContainer ?: throw InterfaceNotImplementedException(context!!, NavigationFragmentContainer::class.java)
    }

    override fun onDetach() {
        container = null
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        path = arguments.getSerializable(KEY_PATH) as List<String>
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_list_navigation, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model?.addListener(listener)
        model?.state?.let { update(it) }
    }

    override fun onDestroyView() {
        model?.removeListener(listener)
        super.onDestroyView()
    }

    private fun update(node: Node<FragmentFactory>) {
        node.findNode(path)?.let { currentNode ->
            val newState = State(currentNode.currentChildTag, currentNode.children.map { it.tag })
            if (newState == lastSavedState) {
                return
            }
            lastSavedState = newState

            val transaction = childFragmentManager.beginTransaction()

            val removeFragmentsOfRemovedNodes = {
                fragments().map { it.tag }
                        .subtract(currentNode.children.map { it.tag })
                        .map { childFragmentManager.findFragmentByTag(it) }
                        .filterNotNull()
                        .forEach { transaction.remove(it) }
            }

            val attachFragmentForCurrentNodeAndDetachAnother = {
                currentNode.children.forEach {
                    var fragment = childFragmentManager.findFragmentByTag(it.tag)
                    if (fragment == null) {
                        fragment = it.value.create(path + it.tag)
                        transaction.add(R.id.placeholder, fragment, it.tag)
                    }
                    if (currentNode.currentChildTag == it.tag) {
                        transaction.attach(fragment)
                    } else {
                        transaction.detach(fragment)
                    }
                }
            }

            removeFragmentsOfRemovedNodes()
            attachFragmentForCurrentNodeAndDetachAnother()

            transaction.commitNow()
        }
    }

    private fun fragments(): List<Fragment> = (childFragmentManager.fragments ?: emptyList()).filterNotNull()

    //region NavigationFragment

    override fun push(tag: String, factory: FragmentFactory) {
        model?.add(tag, factory, path)
        model?.goTo(tag, path)
    }

    override fun pop() {
        model?.let { model ->
            model.state.findNode(path)?.let { node ->
                val children = node.children
                children.getOrNull(children.size - 2)?.let {
                    model.goTo(it.tag, path)
                }
                children.getOrNull(children.size - 1)?.let {
                    model.remove(it.tag, path)
                }
            }
        }
    }

    //endregion

    val listener: ModelListener<FragmentFactory> = object : ModelListener<FragmentFactory> {
        override fun invoke(state: Node<FragmentFactory>) {
            update(state)
        }
    }
}
