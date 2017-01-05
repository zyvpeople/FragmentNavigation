package com.develop.zuzik.fragmentnavigation.navigation.pager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.R
import com.develop.zuzik.fragmentnavigation.exception.FragmentAlreadyExistException
import com.develop.zuzik.fragmentnavigation.exception.FragmentDoesNotExistException
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.FragmentFactory
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationEntry
import com.develop.zuzik.fragmentnavigation.navigation.interfaces.NavigationFragment
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

    lateinit private var viewPager: ViewPager
    lateinit private var adapter: NavigationFragmentPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NavigationFragmentPagerAdapter(childFragmentManager, readNavigationEntries(savedInstanceState))
    }

    private fun readNavigationEntries(savedInstanceState: Bundle?): ArrayList<NavigationEntry> =
            if (savedInstanceState != null) {
                savedInstanceState.getSerializable(KEY_STATE_NAVIGATION_ENTRIES) as ArrayList<NavigationEntry>
            } else {
                arguments.getSerializable(KEY_ARGUMENT_NAVIGATION_ENTRIES) as ArrayList<NavigationEntry>
            }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_pager_navigation, container, false)
        viewPager = view?.findViewById(R.id.viewPager) as ViewPager
        viewPager.adapter = adapter
        return view
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable(KEY_STATE_NAVIGATION_ENTRIES, ArrayList(adapter.entries))
    }

    private fun tags() = adapter.entries.map { it.tag }

    //region NavigationFragment

    override fun addFragment(tag: String, factory: FragmentFactory) {
        if (!tags().contains(tag)) {
            adapter.entries.add(NavigationEntry(tag, factory))
            adapter.notifyDataSetChanged()
        } else {
            throw FragmentAlreadyExistException(tag)
        }
    }

    override fun removeFragment(tag: String) {
        if (tags().contains(tag)) {
            (0..adapter.cachedFragmentsAtPositions.size()).forEach {
                val fragmentPosition = adapter.cachedFragmentsAtPositions.keyAt(it)
                if (tags()[fragmentPosition] == tag) {
                    val cachedFragment = adapter.cachedFragmentsAtPositions[fragmentPosition]
                    adapter.removedFragments.add(cachedFragment)
                }
            }
            adapter.entries.removeAt(tags().indexOf(tag))
            adapter.notifyDataSetChanged()
        } else {
            throw FragmentDoesNotExistException(tag)
        }
    }

    override fun goToFragment(tag: String) {
        if (tags().contains(tag)) {
            viewPager.setCurrentItem(tags().indexOf(tag), true)
        } else {
            throw FragmentDoesNotExistException(tag)
        }
    }

    override fun pushFragment(tag: String, factory: FragmentFactory) {
        addFragment(tag, factory)
        goToFragment(tag)
    }

    override fun popFragment(fail: () -> Unit) {
        val entries = adapter.entries
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
