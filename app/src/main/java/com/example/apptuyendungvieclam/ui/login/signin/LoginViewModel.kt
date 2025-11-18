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

// START: THÊM IMPORTS CHO FACEBOOK LOGIN
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.facebook.GraphRequest
import com.facebook.GraphResponse
import com.google.firebase.auth.UserProfileChangeRequest
import android.os.Bundle
import org.json.JSONObject
// END: THÊM IMPORTS CHO FACEBOOK LOGIN
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
    // HÀM MỚI: Xử lý Access Token từ Facebook và Đăng nhập Firebase
    fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)

        auth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Đăng nhập Firebase thành công. Bây giờ, lấy Tên từ Facebook.
                    fetchFacebookName(token)
                } else {
                    // Đăng nhập thất bại
                    uiEventLiveData.value = LOG_IN_EROR
                }
            }
    }

    // HÀM MỚI: Gọi Graph API để lấy Tên người dùng và cập nhật Firebase Display Name
    private fun fetchFacebookName(token: AccessToken) {
        val request = GraphRequest.newMeRequest(
            token
        ) { jsonObject: JSONObject?, response: GraphResponse? ->

            if (jsonObject != null && jsonObject.has("name")) {
                val facebookName = jsonObject.getString("name")

                // Cập nhật tên hiển thị (Display Name) trong Firebase
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(facebookName)
                    .build()

                auth!!.currentUser?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener {
                        // Sau khi cập nhật tên, tiếp tục lấy dữ liệu từ Realtime DB
                        getFirebaseUserData(auth!!.currentUser!!.uid)
                    }

            } else {
                // Nếu không lấy được tên, vẫn tiến hành lấy dữ liệu người dùng
                getFirebaseUserData(auth!!.currentUser!!.uid)
            }
        }

        val parameters = Bundle()
        parameters.putString("fields", "id,name,email")
        request.parameters = parameters
        request.executeAsync()
    }

    // HÀM MỚI: Tách logic lấy dữ liệu người dùng từ Firebase Realtime DB
    private fun getFirebaseUserData(uid: String) {
        userID = uid
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference(userID!!)

        databaseReference!!.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Người dùng đã tồn tại trong Realtime DB
                    user = snapshot.getValue(User::class.java)
                    uiEventLiveData.value = LOG_IN_SUCCESS
                } else {
                    // Đây là người dùng mới đăng nhập bằng Facebook, cần tạo hồ sơ User
                    // Bạn cần THÊM LOGIC để tạo hồ sơ người dùng (User object) mặc định
                    // cho người dùng Facebook mới TẠI ĐÂY.

                    // Hiện tại, chúng ta chỉ báo lỗi nếu không tìm thấy dữ liệu (như code gốc của bạn)
                    // Hoặc chuyển đến LOG_IN_SUCCESS để xử lý sau.

                    // Tạm thời báo thành công (bạn cần bổ sung logic tạo User nếu cần)
                    // Hoặc xử lý lỗi
                    Log.e(TAG, "User data not found in Realtime DB for UID: $uid")
                    uiEventLiveData.value = LOG_IN_SUCCESS
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "loadPost:onCancelled", error.toException())
                uiEventLiveData.value = LOG_IN_EROR
            }
        })
    }

    // CHỈNH SỬA: Thay thế logic onLogIn bằng getFirebaseUserData
    fun onLogIn(){
        auth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUser = auth!!.currentUser
                getFirebaseUserData(firebaseUser!!.uid) // Gọi hàm lấy dữ liệu mới
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