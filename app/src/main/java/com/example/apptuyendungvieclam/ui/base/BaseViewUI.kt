package com.example.apptuyendungvieclam.ui.base

import androidx.databinding.ViewDataBinding

interface BaseViewUI {
    fun getLayoutMain(): Int

    fun setEvents()

    fun initComponents()

    fun onBackRoot()

    fun showMessage(message: String)

    fun showMessage(messageId: Int)

    val isDestroyView: Boolean

    fun onResumeControl()

    fun onPauseControl()

    fun hideKeyBoard(): Boolean

    fun getBindingData(): ViewDataBinding

}