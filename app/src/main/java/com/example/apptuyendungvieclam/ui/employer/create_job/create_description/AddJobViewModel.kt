package com.example.apptuyendungvieclam.ui.employer.create_job.create_description

import android.app.Activity
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class AddJobViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<CreateJobCallBack>(appDatabase, interactCommon, scheduler) {

    lateinit var jobCode : String
    lateinit var jobName : String
    lateinit var description :String
    lateinit var idAccount : String
    private var idCompany : Int = 0
    private var active : Int? = null

    companion object{
        const val ADD_JOB_SUCCESS = 2
        const val ADD_JOB_ERROR = 3
        const val NEXT_TO_REQUEST = 4
        const val ADD_JOB_EXISTS = 5
        const val GO_TO_PAYER = 6
    }

    fun onCLickNext(){
        uiEventLiveData.value = NEXT_TO_REQUEST
    }

    fun addJobToDatabase(activity : Activity, user: User){
        val sqLiteHelper = SQLiteHelper(activity, "Data.sqlite", null, 5)
        idAccount = user.idAccount
        val dataActive = sqLiteHelper.GetData("SELECT * FROM UserActive WHERE IdUser = '${user.idAccount}'")
        while (dataActive.moveToNext()){
            active = dataActive.getInt(2)
        }
        if(active == 1){
            val cursor = sqLiteHelper.GetData("SELECT * FROM Job4 WHERE JobCode = '$jobCode'")
            if (cursor.count <= 0) {
                val data = sqLiteHelper.GetData("SELECT * FROM User WHERE IdAccount = '$idAccount'")
                while (data.moveToNext()) {
                    idCompany = data.getInt(8)
                }
                if(idCompany == 0){
                    uiEventLiveData.value = ADD_JOB_ERROR
                }else{
                    sqLiteHelper.QueryData("INSERT INTO Job4 VALUES(null,'$jobCode','$jobName','$description','$idAccount','$idCompany','Hihi','','')")
                    uiEventLiveData.value = ADD_JOB_SUCCESS
                }
            } else {
                uiEventLiveData.value = ADD_JOB_EXISTS
            }
        }
        else if(active == 0){
            uiEventLiveData.value = GO_TO_PAYER
        }
    }
}