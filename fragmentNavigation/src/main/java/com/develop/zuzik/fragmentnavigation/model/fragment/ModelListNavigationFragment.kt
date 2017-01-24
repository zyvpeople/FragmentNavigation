package com.develop.zuzik.fragmentnavigation.model.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.model.Model
import com.develop.zuzik.fragmentnavigation.model.ModelListener
import com.develop.zuzik.fragmentnavigation.model.Node
import java.util.*

/**
 * User: zuzik
 * Date: 12/22/16
 */

class ModelListNavigationFragment : Fragment(), ModelNavigationFragment<ModelFragmentFactory> {

    private data class State(val currentChildTag: String?,
                             val childrenTags: List<String>)

    companion object {

        private val KEY_PATH = "KEY_PATH"

        fun create(path: List<String>): ModelListNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_PATH, ArrayList(path))
            val fragment = ModelListNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var path: List<String> = emptyList()
    private var lastSavedState: State? = null
    private var container: ModelNavigationFragmentContainer? = null

    override val model: Model<ModelFragmentFactory>?
        get() = container?.model

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        container = context as? ModelNavigationFragmentContainer ?: throw RuntimeException("$context must implement ${ModelNavigationFragmentContainer::class.simpleName}")
    }

    override fun onDetach() {
        container = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_model_list_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        path = arguments.getSerializable(KEY_PATH) as List<String>
        subscribeOnModel()
    }

    override fun onDestroyView() {
        unsubscribeFromModel()
        super.onDestroyView()
    }

    private fun subscribeOnModel() {
        model?.addListener(listener)
        model?.state?.let { update(it) }
    }

    private fun unsubscribeFromModel() {
        model?.removeListener(listener)
    }

    private fun update(node: Node<ModelFragmentFactory>) {
        node.findNode(path)?.let { currentNode ->
            val newState = State(currentNode.currentChildTag, currentNode.children.map { it.tag })
            if (newState != lastSavedState) {
                lastSavedState = newState
                val transaction = childFragmentManager.beginTransaction()
                fragments().map { it.tag }
                        .subtract(currentNode.children.map { it.tag })
                        .forEach {
                            childFragmentManager.findFragmentByTag(it)?.let {
                                transaction.remove(it)
                            }
                        }
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
                transaction.commitNow()
            }
        }
    }

    private fun fragments(): List<Fragment> = (childFragmentManager.fragments ?: emptyList()).filterNotNull()

    val listener: ModelListener<ModelFragmentFactory> = object : ModelListener<ModelFragmentFactory> {
        override fun invoke(state: Node<ModelFragmentFactory>) {
            update(state)
        }
    }
}
