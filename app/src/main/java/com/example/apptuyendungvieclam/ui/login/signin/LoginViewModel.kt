package com.example.apptuyendungvieclam.ui.login.signin

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.concurrent.Executor
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor) :BaseViewModel<LoginCallBack>(appDatabase,interactCommon,scheduler){

    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var auth : FirebaseAuth? = null
    lateinit var email: String
    lateinit var password: String
    private var user: User? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null
    private var firebaseUser: FirebaseUser? = null
    private var userID: String? = null

    init {
        auth = FirebaseAuth.getInstance()
        firebaseUserMutableLiveData = MutableLiveData()
        if (auth!!.currentUser != null) {
            firebaseUserMutableLiveData!!.postValue(auth!!.currentUser)
        }
    }
    companion object{
        const val LOG_IN_SUCCESS = 1000
        const val LOG_IN_EROR = 1001
        const val START_REGISTER_FAGMENT = 1002
        const val GET_DATA_FROM_UI_AND_LOGIN = 1003
    }

    fun onLogIn(){
            auth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUser = auth!!.currentUser
                    userID = firebaseUser!!.uid
                    firebaseDatabase = FirebaseDatabase.getInstance()
                    databaseReference = firebaseDatabase!!.getReference(userID!!)
                    databaseReference!!.addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            user = snapshot.getValue(User::class.java)
                            uiEventLiveData.value = LOG_IN_SUCCESS
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Log.e(TAG, "loadPost:onCancelled", error.toException())
                        }
                    })
                } else {
                    uiEventLiveData.value = LOG_IN_EROR
                }
            }
    }

    fun onClickStartRegister(){
        uiEventLiveData.value = START_REGISTER_FAGMENT
    }
    fun onClickLogin(){
        uiEventLiveData.value = GET_DATA_FROM_UI_AND_LOGIN
    }
    fun getUser(): User? {
        return user
    }
}