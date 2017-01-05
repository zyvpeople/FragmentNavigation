package com.develop.zuzik.fragmentnavigationsample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_result.*

/**
 * User: zuzik
 * Date: 12/22/16
 */

class ResultFragment : Fragment() {

    companion object {

        fun create() = ResultFragment()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("ResultFragment", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("ResultFragment", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("ResultFragment", "onCreateView")
        return inflater?.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ResultFragment", "onViewCreated")
        ok.setOnClickListener {
            targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
            activity.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d("ResultFragment", "onSaveInstanceState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("ResultFragment", "onViewStateRestored")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("ResultFragment", "onActivityResult $requestCode")
    }

    override fun onStart() {
        super.onStart()
        Log.d("ResultFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ResultFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ResultFragment", "onPause")
    }

    override fun onStop() {
        Log.d("ResultFragment", "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("ResultFragment", "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("ResultFragment", "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("ResultFragment", "onDetach")
        super.onDetach()
    }
}
