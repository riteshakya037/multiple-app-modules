package com.riteshakya.core.platform

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.riteshakya.core.extension.toPixels
import timber.log.Timber

object LoadingUiHelper {

    enum class Type {
        FULL_SCREEN,
        DIALOG
    }

    fun showProgress(
        fragmentManager: androidx.fragment.app.FragmentManager,
        type: Type = LoadingUiHelper.Type.FULL_SCREEN
    ): ProgressDialogFragment {
        return ProgressDialogFragment().apply {
            arguments = Bundle().apply {
                putSerializable(LoadingUiHelper.ProgressDialogFragment.KEY_TYPE, type)
            }
            show(fragmentManager, LoadingUiHelper.ProgressDialogFragment.TAG)
        }
    }

    class ProgressDialogFragment : androidx.fragment.app.DialogFragment() {

        private var type: Type? = null

        companion object {
            var KEY_TYPE = "KEY_TYPE"
            val TAG = "ProgressDialogFragment"
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            isCancelable = false
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            Timber.d("onCreateView ${arguments?.getSerializable(KEY_TYPE)}")
            type =
                savedInstanceState?.getSerializable(KEY_TYPE) as Type? ?: arguments?.getSerializable(
                    KEY_TYPE
                ) as Type?
                        ?: LoadingUiHelper.Type.FULL_SCREEN

            val frameLayout = FrameLayout(context!!)
            var lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            frameLayout.layoutParams = lp

            val progressBar = ProgressBar(context)
            lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            lp.gravity = Gravity.CENTER
            val margin = context!!.toPixels(16)
            lp.setMargins(margin, margin, margin, margin)
            progressBar.layoutParams = lp

            frameLayout.addView(progressBar)

            return frameLayout
        }

        override fun onStart() {
            super.onStart()
            if (dialog == null) return
            val window = dialog!!.window
            when (type) {
                LoadingUiHelper.Type.DIALOG -> {
                    val display = window!!.windowManager.defaultDisplay
                    val size = Point()
                    display.getSize(size)
                    val width = size.x
                    window.setLayout((width * 0.9f).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
                }
                LoadingUiHelper.Type.FULL_SCREEN -> {
                    window!!.setBackgroundDrawable(null)
                }
            }
        }

        override fun onSaveInstanceState(outState: Bundle) {
            super.onSaveInstanceState(outState)
            outState.putSerializable(KEY_TYPE, type)
        }
    }
}