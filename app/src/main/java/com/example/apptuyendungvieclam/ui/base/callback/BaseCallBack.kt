package com.example.apptuyendungvieclam.ui.base.callback

import com.example.apptuyendungvieclam.ui.base.BaseViewUI

interface BaseCallBack : BaseViewUI {
    fun error(id: String, error: Throwable)
}