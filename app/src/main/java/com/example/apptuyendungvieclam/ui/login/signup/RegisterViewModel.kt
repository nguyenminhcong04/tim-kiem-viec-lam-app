package com.example.apptuyendungvieclam.ui.login.signup

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.Executor
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<RegisterCallBack>(appDatabase, interactCommon, scheduler) {

    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var auth : FirebaseAuth? = null
    lateinit var email: String
    lateinit var password: String
    lateinit var userName: String
    var age: Int = 0
    lateinit var phone: String
    var permission : Int = 0
    private var mAuth: FirebaseAuth? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    init {
        auth = FirebaseAuth.getInstance()
        firebaseUserMutableLiveData = MutableLiveData()
        if (auth!!.currentUser != null) {
            firebaseUserMutableLiveData!!.postValue(auth!!.currentUser)
        }
    }

    companion object {
        const val REGISTER_SUCCESS = 1000
        const val BACK_LOGIN = 1002
        const val REGISTER_ERROR = 1003
        const val GET_DATA_FROM_UI_AND_REGISTER = 1005
    }

    fun onClickBackLogInFragment() {
        uiEventLiveData.value = BACK_LOGIN
    }

    fun onRegister(activity : Activity) {
        val sqLiteHelper = SQLiteHelper(activity, "Data.sqlite", null, 5)
        auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseDatabase = FirebaseDatabase.getInstance()
                databaseReference = firebaseDatabase!!.reference
                val IdAcount: String = task.result.user!!.uid
                val user = User(IdAcount,email,password,userName,age,phone,permission)
                sqLiteHelper.QueryData("INSERT INTO User VALUES(null,'$IdAcount','$email','$password','$userName','$age','$phone','$permission','0')")
                sqLiteHelper.QueryData("INSERT INTO UserAvatar VALUES(null,'${user.idAccount}','')")
                sqLiteHelper.QueryData("INSERT INTO UserActive VALUES(null,'${user.idAccount}','0')")
                databaseReference!!.child(IdAcount).setValue(user)
                uiEventLiveData.value = REGISTER_SUCCESS
            } else {
                uiEventLiveData.value = REGISTER_ERROR
            }
        }
    }
    fun onClickSignUp(){
        uiEventLiveData.value = GET_DATA_FROM_UI_AND_REGISTER
    }
}