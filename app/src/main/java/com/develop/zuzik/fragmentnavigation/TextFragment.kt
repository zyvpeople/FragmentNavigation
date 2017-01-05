package com.develop.zuzik.fragmentnavigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.fragmentnavigation.navigation_fragment.NavigationFragment
import kotlinx.android.synthetic.main.fragment_text.*

/**
 * User: zuzik
 * Date: 12/22/16
 */

class TextFragment : Fragment() {

    companion object {

        private val KEY_TAG = "KEY_TAG"

        fun create(text: String): TextFragment {
            val bundle = Bundle()
            bundle.putString(KEY_TAG, text)
            val fragment = TextFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var text = ""

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("TextFragment", "$text onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        text = arguments.getString(KEY_TAG)
        Log.d("TextFragment", "$text onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TextFragment", "$text onCreateView")
        return inflater?.inflate(R.layout.fragment_text, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TextFragment", "$text onViewCreated")
        fragmentTag.text = text
        pushFragment.setOnClickListener {
            parentNavigationFragment()?.pushFragment(nextFragmentTitle.text.toString(), TextFragmentFactory(nextFragmentTitle.text.toString()))
        }
        popFragment.setOnClickListener {
            parentNavigationFragment()?.popFragment { }
        }
        startActivity.setOnClickListener {
            startActivityForResult(Intent(context, ResultActivity::class.java), 1)
        }
        startDialog.setOnClickListener {
            val dialogFragment = TextDialogFragment()
            dialogFragment.setTargetFragment(this, 2)
            dialogFragment.show(fragmentManager, "dialogFragment")
        }
        startFragmentForResult.setOnClickListener {
            TODO()
//            val fragment = ResultFragment.create()
//            fragment.setTargetFragment(this, 3)
//            (context as NavigationContainer).pushChild(fragment)
        }
        startBottomSheet.setOnClickListener {
            val dialogFragment = TextBottomSheetDialogFragment()
            dialogFragment.setTargetFragment(this, 4)
            dialogFragment.show(fragmentManager, "bottomSheetFragment")
        }
        startSettingsFragment.setOnClickListener {
            parentNavigationFragment()?.pushFragment(nextFragmentTitle.text.toString(), SettingsFragmentFactory())
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d("TextFragment", "$text onSaveInstanceState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("TextFragment", "$text onViewStateRestored")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TextFragment", "$text onActivityResult $requestCode")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TextFragment", "$text onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TextFragment", "$text onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TextFragment", "$text onPause")
    }

    override fun onStop() {
        Log.d("TextFragment", "$text onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("TextFragment", "$text onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("TextFragment", "$text onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("TextFragment", "$text onDetach")
        super.onDetach()
    }

    private fun parentNavigationFragment(): NavigationFragment? =
            parentFragment as? NavigationFragment
}
