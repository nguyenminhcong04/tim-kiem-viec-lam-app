package com.example.apptuyendungvieclam.ui.account

import android.content.Context
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<ProfileCallBack>(appDatabase, interactCommon, scheduler) {

    private var auth : FirebaseAuth? = null

    companion object{
        const val LOG_OUT = 1000
        const val GO_UPDATE_SKILL = 1001
        const val SHOW_DIALOG = 1002
        const val ON_CLICK_AVT = 1003
        const val ON_CLICK_SET_AVT = 1004
        const val ON_CLICK_COMPANY = 1005
        const val ON_CLICK_INFORMATION = 1006
        const val ON_CLICK_PAY = 1007
    }
    init {
        auth = FirebaseAuth.getInstance()
    }

    fun onClickUpdateSkill(){
        uiEventLiveData.value = GO_UPDATE_SKILL
    }

    fun onCLickLogOut(){
        uiEventLiveData.value = LOG_OUT
    }

    fun onCLickShowDialog(){
        uiEventLiveData.value = SHOW_DIALOG
    }

    fun onLogOut(){
        FirebaseAuth.getInstance().signOut()
    }
    fun onClickAvatar(){
        uiEventLiveData.value = ON_CLICK_AVT
    }
    fun onClickSetAvatar(){
        uiEventLiveData.value = ON_CLICK_SET_AVT
    }
    fun onClickCompany(){
        uiEventLiveData.value = ON_CLICK_COMPANY
    }
    fun onClickInformation(){
        uiEventLiveData.value = ON_CLICK_INFORMATION
    }
    fun onClickPay(){
        uiEventLiveData.value = ON_CLICK_PAY
    }
    fun saveAvatarToDB(user: User,strAvatar: String,context: Context){
        val sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("UPDATE UserAvatar SET avatar = '$strAvatar' WHERE IdUser ='${user.idAccount}'")
    }
}