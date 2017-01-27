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
    private var lastSavedState: State? = null
    private var container: ModelNavigationFragmentContainer? = null
    lateinit private var viewPager: ViewPager
    private var adapter: ModelNavigationFragmentPagerAdapter? = null

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
        viewPager.addOnPageChangeListener(onPageChangeListener)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeOnModel()
    }

    override fun onDestroyView() {
        viewPager.removeOnPageChangeListener(onPageChangeListener)
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
            if (newState == lastSavedState) {
                return
            }
            lastSavedState = newState

            if (adapter == null) {
                adapter = ModelNavigationFragmentPagerAdapter(childFragmentManager, currentNode, path)
                viewPager.adapter = adapter
            } else {
                adapter?.node = currentNode
            }

            adapter?.notifyDataSetChanged()
            viewPager.setCurrentItem(currentNode.children.indexOfFirst { it.tag == currentNode.currentChildTag }, true)
        }
    }

    val listener: ModelListener<ModelFragmentFactory> = object : ModelListener<ModelFragmentFactory> {
        override fun invoke(state: Node<ModelFragmentFactory>) {
            update(state)
        }
    }

    val onPageChangeListener: ViewPager.SimpleOnPageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            model?.state?.findNode(path)?.children?.getOrNull(position)?.let {
                lastSavedState = lastSavedState?.copy(currentChildTag = it.tag)
                model?.goTo(it.tag, path)
            }
        }
    }
}
