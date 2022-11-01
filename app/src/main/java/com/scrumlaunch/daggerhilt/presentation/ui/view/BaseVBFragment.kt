package com.scrumlaunch.daggerhilt.presentation.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseVBFragment<VBinding : ViewDataBinding>(private val layoutId: Int) :
    BaseFragment() {

    private var _vBinding: VBinding? = null
    val binding: VBinding get() = _vBinding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vBinding = DataBindingUtil.inflate(layoutInflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
    }

    open fun setUpViews() {}

    open fun observeData() {}


    override fun onDestroyView() {
        _vBinding = null
        super.onDestroyView()
    }

}