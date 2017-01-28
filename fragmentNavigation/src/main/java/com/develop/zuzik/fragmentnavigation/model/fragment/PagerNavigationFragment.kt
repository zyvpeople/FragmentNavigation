package com.develop.zuzik.fragmentnavigation.model.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
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
 * Date: 12/24/16
 */
class PagerNavigationFragment : Fragment(), NavigationFragment {

    companion object {

        private val KEY_PATH = "KEY_PATH"

        fun create(path: List<String>): PagerNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_PATH, ArrayList(path))
            val fragment = PagerNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var path: List<String> = emptyList()
    private var lastSavedState: State? = null
    private var container: NavigationFragmentContainer? = null
    private var viewPager: ViewPager? = null
    private var adapter: NavigationFragmentPagerAdapter? = null

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pager_navigation, container, false)
        viewPager = view?.findViewById(R.id.viewPager) as ViewPager
        viewPager?.addOnPageChangeListener(onPageChangeListener)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model?.addListener(listener)
        model?.state?.let { update(it) }
    }

    override fun onDestroyView() {
        model?.removeListener(listener)
        viewPager?.removeOnPageChangeListener(onPageChangeListener)
        super.onDestroyView()
    }

    private fun update(node: Node<FragmentFactory>) {
        node.findNode(path)?.let { currentNode ->
            val newState = State(currentNode.currentChildTag, currentNode.children.map { it.tag })
            if (newState == lastSavedState) {
                return
            }
            lastSavedState = newState

            if (adapter == null) {
                adapter = NavigationFragmentPagerAdapter(childFragmentManager, currentNode, path)
                viewPager?.adapter = adapter
            } else {
                adapter?.node = currentNode
            }

            adapter?.notifyDataSetChanged()
            viewPager?.setCurrentItem(currentNode.children.indexOfFirst { it.tag == currentNode.currentChildTag }, true)
        }
    }

    //TODO: duplicated with ListNavigationFragment
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

    val onPageChangeListener: ViewPager.SimpleOnPageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            model?.state?.findNode(path)?.children?.getOrNull(position)?.let {
                lastSavedState = lastSavedState?.copy(currentChildTag = it.tag)
                model?.goTo(it.tag, path)
            }
        }
    }
}
