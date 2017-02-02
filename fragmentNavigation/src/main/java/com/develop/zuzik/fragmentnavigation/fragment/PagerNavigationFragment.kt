package com.develop.zuzik.fragmentnavigation.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.exception.InterfaceNotImplementedException
import com.develop.zuzik.fragmentnavigation.fragment.interfaces.Event
import com.develop.zuzik.fragmentnavigation.fragment.interfaces.EventHandler
import com.develop.zuzik.fragmentnavigation.fragment.interfaces.NavigationFragment
import com.develop.zuzik.fragmentnavigation.fragment.interfaces.NavigationFragmentContainer
import com.develop.zuzik.fragmentnavigation.fragment.scheme.FragmentFactory
import com.develop.zuzik.fragmentnavigation.fragment.scheme.add
import com.develop.zuzik.fragmentnavigation.scheme.Node
import com.develop.zuzik.fragmentnavigation.scheme.Scheme
import com.develop.zuzik.fragmentnavigation.scheme.SchemeListener
import java.util.*

/**
 * User: zuzik
 * Date: 12/24/16
 */
class PagerNavigationFragment : Fragment(), NavigationFragment, EventHandler {

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

    private val scheme: Scheme<FragmentFactory>?
        get() = container?.scheme

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
        scheme?.addListener(listener)
        scheme?.state?.let { update(it) }
    }

    override fun onDestroyView() {
        scheme?.removeListener(listener)
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
        scheme?.add(tag, factory, path)
        scheme?.goTo(tag, path)
    }

    override fun pop(fail: () -> Unit) {
        val scheme = scheme
        if (scheme != null) {
            val node = scheme.state.findNode(path)
            if (node != null) {
                val children = node.children
                if (children.size > 1) {
                    children.getOrNull(children.size - 2)?.let {
                        scheme.goTo(it.tag, path)
                    }
                    children.getOrNull(children.size - 1)?.let {
                        scheme.remove(it.tag, path)
                    }
                    return
                }
            }
        }
        fail()
    }

    //endregion

    //region EventHandler

    override fun handleEvent(event: Event): Boolean {
        val eventHandler = scheme
                ?.state
                ?.findNode(path)
                ?.currentChildTag
                ?.let { adapter?.findFragmentByTag(it) } as? EventHandler
        return eventHandler?.handleEvent(event) ?: false
    }

    //endregion

    val listener: SchemeListener<FragmentFactory> = object : SchemeListener<FragmentFactory> {
        override fun invoke(state: Node<FragmentFactory>) {
            update(state)
        }
    }

    val onPageChangeListener: ViewPager.SimpleOnPageChangeListener = object : ViewPager.SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            scheme?.state?.findNode(path)?.children?.getOrNull(position)?.let {
                lastSavedState = lastSavedState?.copy(currentChildTag = it.tag)
                scheme?.goTo(it.tag, path)
            }
        }
    }
}
