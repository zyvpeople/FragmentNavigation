package com.develop.zuzik.fragmentnavigation.model.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
    private var lastSavedCurrentNode: Node<ModelFragmentFactory>? = null
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
        Node<ModelFragmentFactory>("", null, null, mutableListOf(node)).findNode(path)?.let { currentNode ->
            if (currentNode != lastSavedCurrentNode) {
                lastSavedCurrentNode = currentNode
                val transaction = childFragmentManager.beginTransaction()
                fragments().map { it.tag }
                        .subtract(currentNode.children.map { it.tag })
                        .forEach {
                            childFragmentManager.findFragmentByTag(it)?.let {
                                transaction.remove(it)
                            }
                        }
                currentNode.children.forEach {
                    val factory = it.value
                    if (factory != null) {
                        var fragment = childFragmentManager.findFragmentByTag(it.tag)
                        if (fragment == null) {
                            fragment = factory.create(path + it.tag)
                            transaction.add(R.id.placeholder, fragment, it.tag)
                        }
                        if (currentNode.currentChildTag == it.tag) {
                            transaction.attach(fragment)
                        } else {
                            transaction.detach(fragment)
                        }
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
