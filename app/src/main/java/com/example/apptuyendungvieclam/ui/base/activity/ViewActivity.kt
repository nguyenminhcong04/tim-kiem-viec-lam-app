package com.example.apptuyendungvieclam.ui.base.activity

import android.os.Bundle
import android.view.View
import com.example.apptuyendungvieclam.ui.base.BaseViewUI
import com.example.apptuyendungvieclam.ui.base.fragment.BaseFragment

interface ViewActivity : BaseViewUI{

    fun onCreateControl(savedInstanceState: Bundle?)

    fun onDestroyControl()

    fun findFragmentByTag(tag: String): BaseFragment

    fun setViewRoot(viewRoot: View)

    fun onBackParent()

    fun onStartControl()

    fun onStopControl()
}