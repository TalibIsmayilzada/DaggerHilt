package com.scrumlaunch.daggerhilt.presentation.ui.view

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    lateinit var baseActivity: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity: BaseActivity = context
            this.baseActivity = activity
        }

    }

    fun show(message: String, useToast: Boolean = false) {
        baseActivity.show(message, useToast)
    }

}