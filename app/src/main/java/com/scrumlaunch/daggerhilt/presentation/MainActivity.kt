package com.scrumlaunch.daggerhilt.presentation

import android.os.Bundle
import com.scrumlaunch.daggerhilt.R
import com.scrumlaunch.daggerhilt.presentation.ui.view.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}