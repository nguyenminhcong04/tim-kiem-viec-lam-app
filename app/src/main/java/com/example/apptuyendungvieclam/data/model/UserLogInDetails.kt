package com.example.apptuyendungvieclam.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class UserLogInDetails : BaseObservable(){

    var email: String? =null
    @Bindable get() = field
    set(email){
        field = email
  //      notifyPropertyChanged(BR.email)
    }

    var password: String? =null
        @Bindable get() = field
        set(password){
            field = password
  //          notifyPropertyChanged(BR.password)
        }
}