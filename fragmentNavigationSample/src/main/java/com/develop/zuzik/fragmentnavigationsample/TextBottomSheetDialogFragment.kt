package com.develop.zuzik.fragmentnavigationsample

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_text_bottom_sheet_dialog.view.*

/**
 * User: zuzik
 * Date: 12/24/16
 */
class TextBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun setupDialog(dialog: Dialog?, style: Int) {
        super.setupDialog(dialog, style)
        val view = View.inflate(context, R.layout.fragment_text_bottom_sheet_dialog, null)
        view.ok.setOnClickListener {
            targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, null)
            dismiss()
        }
        dialog?.setContentView(view)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        Log.d("TextBottomSheet", "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TextBottomSheet", "onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("TextBottomSheet", "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TextBottomSheet", "onViewCreated")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d("TextBottomSheet", "onSaveInstanceState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("TextBottomSheet", "onViewStateRestored")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("TextBottomSheet", "onActivityResult $requestCode")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TextBottomSheet", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TextBottomSheet", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TextBottomSheet", "onPause")
    }

    override fun onStop() {
        Log.d("TextBottomSheet", "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d("TextBottomSheet", "onDestroyView")
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d("TextBottomSheet", "onDestroy")
        super.onDestroy()
    }

    override fun onDetach() {
        Log.d("TextBottomSheet", "onDetach")
        super.onDetach()
    }
}