package com.develop.zuzik.fragmentnavigation

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * User: zuzik
 * Date: 12/24/16
 */
class TextDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("TextDialogFragment", "onCreateDialog")
        return AlertDialog.Builder(context)
                .setTitle("Dialog")
                .setPositiveButton("Button", DialogInterface.OnClickListener { dialogInterface, button ->
                    targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
                })
                .create()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("TextDialogFragment", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TextDialogFragment", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TextDialogFragment", "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TextDialogFragment", "onViewCreated")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d("TextDialogFragment", "onSaveInstanceState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("TextDialogFragment", "onViewStateRestored")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TextDialogFragment", "onActivityResult $requestCode")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TextDialogFragment", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TextDialogFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TextDialogFragment", "onPause")
    }

    override fun onStop() {
        Log.d("TextDialogFragment", "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("TextDialogFragment", "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("TextDialogFragment", "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("TextDialogFragment", "onDetach")
        super.onDetach()
    }
}