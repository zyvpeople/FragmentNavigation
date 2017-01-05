package com.develop.zuzik.fragmentnavigation.pager_navigation_fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.exceptions.FragmentAlreadyExistException
import com.develop.zuzik.fragmentnavigation.exceptions.FragmentDoesNotExistException
import com.develop.zuzik.fragmentnavigation.navigation_fragment.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationEntry
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment
import kotlinx.android.synthetic.main.fragment_pager_navigation.*
import java.util.*

/**
 * User: zuzik
 * Date: 12/24/16
 */
class PagerNavigationFragment : Fragment(), NavigationFragment {

    companion object {

        private val KEY_ARGUMENT_NAVIGATION_ENTRIES = "KEY_ARGUMENT_NAVIGATION_ENTRIES"
        private val KEY_STATE_NAVIGATION_ENTRIES = "KEY_STATE_NAVIGATION_ENTRIES"

        fun create(entries: List<NavigationEntry>): PagerNavigationFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_ARGUMENT_NAVIGATION_ENTRIES, ArrayList(entries))
            val fragment = PagerNavigationFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var adapter: NavigationFragmentPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (adapter == null) {
            if (savedInstanceState != null) {
                val entries = savedInstanceState.getSerializable(KEY_STATE_NAVIGATION_ENTRIES) as ArrayList<NavigationEntry>
                adapter = NavigationFragmentPagerAdapter(childFragmentManager, entries)
            } else {
                val entries = arguments.getSerializable(KEY_ARGUMENT_NAVIGATION_ENTRIES) as ArrayList<NavigationEntry>
                adapter = NavigationFragmentPagerAdapter(childFragmentManager, entries)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_pager_navigation, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(KEY_STATE_NAVIGATION_ENTRIES, ArrayList(adapter!!.entries))
    }

    //region NavigationFragment

    override fun addFragment(tag: String, factory: FragmentFactory) {
        if (!adapter!!.entries.map { it.tag }.contains(tag)) {
            adapter!!.entries.add(NavigationEntry(tag, factory))
            adapter!!.notifyDataSetChanged()
        } else {
            throw FragmentAlreadyExistException(tag)
        }
    }

    override fun removeFragment(tag: String) {
        if (adapter!!.entries.map { it.tag }.contains(tag)) {
            (0..adapter!!.cachedFragmentsAtPositions.size()).forEach {
                val fragmentPosition = adapter!!.cachedFragmentsAtPositions.keyAt(it)
                if (adapter!!.entries.map { it.tag }[fragmentPosition] == tag) {
                    val cachedFragment = adapter!!.cachedFragmentsAtPositions[fragmentPosition]
                    adapter!!.removedFragments.add(cachedFragment)
                }
            }
            adapter!!.entries.removeAt(adapter!!.entries.map { it.tag }.indexOf(tag))
            adapter!!.notifyDataSetChanged()
        } else {
            throw FragmentDoesNotExistException(tag)
        }
    }

    override fun goToFragment(tag: String) {
        if (adapter!!.entries.map { it.tag }.contains(tag)) {
            viewPager.setCurrentItem(adapter!!.entries.indexOf(adapter!!.entries.find { it.tag == tag }), true)
        } else {
            throw FragmentDoesNotExistException(tag)
        }
    }

    override fun pushFragment(tag: String, factory: FragmentFactory) {
        addFragment(tag, factory)
        goToFragment(tag)
    }

    override fun popFragment(fail: () -> Unit) {
        val entries = adapter!!.entries
        val oldTopEntry = entries.getOrNull(entries.size - 1)
        val newTopEntry = entries.getOrNull(entries.size - 2)
        if (oldTopEntry != null && newTopEntry != null) {
            removeFragment(oldTopEntry.tag)
            goToFragment(newTopEntry.tag)
        } else {
            fail()
        }
    }

    //endregion
}
