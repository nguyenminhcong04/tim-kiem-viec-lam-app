package com.example.apptuyendungvieclam.ui.utils

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.ui.customview.GlideApp

object LoadDataBinding {
    @JvmStatic
    @BindingAdapter("urlImage")
    fun AppCompatImageView.setUrlImage(urlImage: String?){
        if (!StringUtils.isBlank(urlImage)){
            GlideApp.with(this)
                .load(urlImage)
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_error)
                .into(this)
        }
    }
}