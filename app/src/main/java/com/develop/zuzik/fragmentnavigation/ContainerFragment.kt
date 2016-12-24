package com.develop.zuzik.fragmentnavigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * User: zuzik
 * Date: 12/22/16
 */

class ContainerFragment : Fragment() {

    companion object {

        private val KEY_FACTORY = "KEY_FACTORY"

        fun create(factory: TextFragmentFactory): ContainerFragment {
            val bundle = Bundle()
            bundle.putSerializable(KEY_FACTORY, factory)
            val fragment = ContainerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    fun placeholderId() = R.id.childPlaceholder

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_container, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        childFragmentManager
                .beginTransaction()
                .replace(placeholderId(), (arguments.getSerializable(KEY_FACTORY) as TextFragmentFactory).create())
                .addToBackStack(null)
                .commit()
    }
}
