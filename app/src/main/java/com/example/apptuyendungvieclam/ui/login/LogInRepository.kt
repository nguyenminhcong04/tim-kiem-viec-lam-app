package com.example.apptuyendungvieclam.ui.login

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LogInRepository {
    private var application : Application?=null
    private var firebaseUserMutableLiveData: MutableLiveData<FirebaseUser>? = null
    private var auth: FirebaseAuth?=null

    fun getFirebaseUserMutableLiveData(): MutableLiveData<FirebaseUser>? {
        return firebaseUserMutableLiveData
    }

    constructor(application: Application){
        this.application = application
        firebaseUserMutableLiveData = MutableLiveData()
        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser!=null){
            firebaseUserMutableLiveData!!.postValue(auth!!.currentUser)
        }
    }
    fun onregister(email:String,password:String){
        auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUserMutableLiveData!!.postValue(auth!!.currentUser)
            } else {
//                Toast.makeText(application, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun onsignin(email: String,password: String){
            auth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUserMutableLiveData!!.postValue(auth!!.currentUser)
                } else {
//                    Toast.makeText(application, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}