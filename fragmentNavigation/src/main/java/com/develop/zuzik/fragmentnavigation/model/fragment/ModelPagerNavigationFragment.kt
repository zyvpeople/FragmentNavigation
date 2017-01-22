package com.develop.zuzik.fragmentnavigation.model.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
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
 * Date: 12/24/16
 */
class ModelPagerNavigationFragment : Fragment(), ModelNavigationFragment<ModelFragmentFactory> {

    companion object {

        private val KEY_PATH = "KEY_PATH"

        fun create(path: List<String>): ModelPagerNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_PATH, ArrayList(path))
            val fragment = ModelPagerNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var path: List<String> = emptyList()
    //TODO: incorrect because child node can be changed and parent will update screen, need to check by children and current position
    private var lastSavedCurrentNode: Node<ModelFragmentFactory>? = null
    private var container: ModelNavigationFragmentContainer? = null
    lateinit private var viewPager: ViewPager
    lateinit private var adapter: ModelNavigationFragmentPagerAdapter

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        path = arguments.getSerializable(KEY_PATH) as List<String>
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pager_navigation, container, false)
        viewPager = view?.findViewById(R.id.viewPager) as ViewPager
        adapter = ModelNavigationFragmentPagerAdapter(childFragmentManager, null, path)
        viewPager.adapter = adapter
        return view
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
        model?.addListener(listener)
        model?.state?.let { update(it) }
    }

    private fun unsubscribeFromModel() {
        model?.removeListener(listener)
    }

    private fun update(node: Node<ModelFragmentFactory>) {
        node.findNode(path)?.let { currentNode ->
            adapter.node = currentNode
            adapter.notifyDataSetChanged()
            viewPager.setCurrentItem(currentNode.children.indexOfFirst { it.tag == currentNode.currentChildTag }, true)
        }
    }

    val listener: ModelListener<ModelFragmentFactory> = object : ModelListener<ModelFragmentFactory> {
        override fun invoke(state: Node<ModelFragmentFactory>) {
            update(state)
        }
    }
}
