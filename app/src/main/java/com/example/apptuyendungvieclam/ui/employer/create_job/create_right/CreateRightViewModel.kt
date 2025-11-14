package com.example.apptuyendungvieclam.ui.employer.create_job.create_right

import android.content.Context
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class CreateRightViewModel  @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<CreateRightCallBack>(appDatabase, interactCommon, scheduler) {

    lateinit var idJob : String
    companion object{
        const val ON_CLICK_NEXT = 1
        const val ADD_RIGHT_SUCCESS = 3
    }

    fun onClickNext(){
        uiEventLiveData.value = ON_CLICK_NEXT
    }
    fun setRightAndAmount(right : String,amount : Int,context : Context){
        val  sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("UPDATE Job4 SET right = '$right',amount = '$amount' WHERE JobCode ='$idJob'")
        uiEventLiveData.value = ADD_RIGHT_SUCCESS
    }
}