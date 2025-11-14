package com.example.apptuyendungvieclam.ui.xample

import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.databinding.ActivityMainBinding
import com.example.apptuyendungvieclam.ui.base.activity.BaseMVVMActivity
import com.example.apptuyendungvieclam.ui.utils.OpenFragmentUtils

class MainActivity : BaseMVVMActivity<MainCallBack, MainViewModel>(), MainCallBack {

    override fun getLayoutMain() = R.layout.activity_main

    override fun setEvents() {
    }

    override fun initComponents() {
        getBindingData().viewModel = mModel
        OpenFragmentUtils.openUserFragment(supportFragmentManager)
    }

    override fun getViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }

    override fun getBindingData() = mBinding as ActivityMainBinding

}