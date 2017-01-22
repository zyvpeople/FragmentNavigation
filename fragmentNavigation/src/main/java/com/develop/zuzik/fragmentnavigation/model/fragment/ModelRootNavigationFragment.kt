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

/**
 * User: zuzik
 * Date: 1/21/17
 */
class ModelRootNavigationFragment : Fragment(), ModelNavigationFragment<ModelFragmentFactory> {

    override val model: Model<ModelFragmentFactory>?
        get() = container?.model

    private var container: ModelNavigationFragmentContainer? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        container = context as? ModelNavigationFragmentContainer ?: throw RuntimeException("$context must implement ${ModelNavigationFragmentContainer::class.simpleName}")
    }

    override fun onDetach() {
        container = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_model_root_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnModel()
    }

    override fun onDestroyView() {
        unsubscribeFromModel()
        super.onDestroyView()
    }

    private fun subscribeOnModel() {
        model?.addListener(modelListener)
        model?.state?.let { update(it) }
    }

    private fun unsubscribeFromModel() {
        model?.removeListener(modelListener)
    }

    private fun update(node: Node<ModelFragmentFactory>) {
        if (childFragmentManager.findFragmentByTag(node.tag) == null) {
            childFragmentManager
                    .beginTransaction()
                    .add(R.id.placeholder, node.value.create(listOf(node.tag)), node.tag)
                    .commitNow()
        }
    }

    private val modelListener: ModelListener<ModelFragmentFactory> = object : ModelListener<ModelFragmentFactory> {
        override fun invoke(state: Node<ModelFragmentFactory>) {
            update(state)
        }
    }
}