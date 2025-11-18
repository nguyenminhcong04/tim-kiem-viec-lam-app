package com.example.apptuyendungvieclam.ui.login.signin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.databinding.FragmentLoginBinding
import com.example.apptuyendungvieclam.ui.CustomProgressDialog
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.candidate.CandidateMainActivity
import com.example.apptuyendungvieclam.ui.employer.EmployerMainActivity
import com.example.apptuyendungvieclam.ui.login.signup.RegisterFragment

import com.facebook.CallbackManager // THÊM IMPORT
import com.facebook.FacebookCallback // THÊM IMPORT
import com.facebook.FacebookException // THÊM IMPORT
import com.facebook.login.LoginResult // THÊM IMPORT
import java.util.Arrays // THÊM IMPORT

class LoginFragment : BaseMvvmFragment<LoginCallBack, LoginViewModel>(), LoginCallBack {

    private val TAG = LoginFragment::class.java.simpleName
    private val saveInformation = "tk_mk"
    private lateinit var dialog: CustomProgressDialog
    // KHAI BÁO BIẾN CHO FACEBOOK
    private lateinit var callbackManager: CallbackManager
    private var email: String = ""
    private var password: String = ""

    override fun getLayoutMain(): Int {
        return R.layout.fragment_login
    }

    override fun initComponents() {
        // Khởi tạo Callback Manager
        callbackManager = CallbackManager.Factory.create()

        getBindingData().loginViewModel = mModel
        getBindingData().lifecycleOwner = viewLifecycleOwner
        // SỬ DỤNG requireContext() AN TOÀN HƠN
        dialog = CustomProgressDialog(requireContext())

        mModel.uiEventLiveData.observe(viewLifecycleOwner) { event ->
            event?.let {
                when (it) {
                    BaseViewModel.FINISH_ACTIVITY -> requireActivity().finish() // Dùng requireActivity().finish()
                    LoginViewModel.START_REGISTER_FAGMENT -> getFragmentRegiter()
                    LoginViewModel.LOG_IN_SUCCESS -> getLogInSuccess()
                    LoginViewModel.LOG_IN_EROR -> getLogInError()
                    LoginViewModel.GET_DATA_FROM_UI_AND_LOGIN -> logIn()
                }
            }
        }

        // Khởi tạo các sự kiện (bao gồm Facebook)
        setEvents()
    }

    override fun setEvents() {
        // Cấu hình Nút Facebook Login
        // LƯU Ý: Bạn cần phải thêm <com.facebook.login.widget.LoginButton> có id là @+id/btnLoginFacebook
        // vào file fragment_login.xml nếu chưa có.
        // Tuy nhiên, layout của bạn chỉ có Button btnLogin và btnRegister.
        // TÔI GIẢ ĐỊNH BẠN SẼ THÊM NÚT FACEBOOK HOẶC SỬ DỤNG MỘT NÚT THƯỜNG ĐỂ KÍCH HOẠT.
        // TÔI DỰA VÀO CÁI TÊN LOGIC MÀ BẠN CUNG CẤP Ở LẦN TRƯỚC: getBindingData().btnLoginFacebook

        // CẤU HÌNH FACEBOOK LOGIN BUTTON (Nếu bạn sử dụng thư viện LoginButton)
        // Nếu không có btnLoginFacebook, code này sẽ crash.
        // Bạn cần tự thêm <com.facebook.login.widget.LoginButton> vào layout
        getBindingData().btnLoginFacebook.setPermissions(Arrays.asList("email", "public_profile"))
        getBindingData().btnLoginFacebook.fragment = this

        getBindingData().btnLoginFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            // SỬ DỤNG onSuccess, onCancel, onFailure (phổ biến)
            override fun onSuccess(result: LoginResult) {
                dialog.show()
                mModel.handleFacebookAccessToken(result.accessToken)
                Log.d(TAG, "Facebook Login Success: ${result.accessToken.token}")
            }

            override fun onCancel() {
                getBindingData().tvThongBaoDangNhap.text = getString(R.string.facebook_login_cancelled)
            }

            // THAY THẾ onFailure BẰNG onError
            override fun onError(error: FacebookException) {
                // Xử lý khi có lỗi
                dialog.dismiss() // Đảm bảo ẩn dialog khi có lỗi
                getBindingData().tvThongBaoDangNhap.text = "Lỗi đăng nhập Facebook: ${error.message}"
                Log.e(TAG, "Facebook Login Failure", error)
            }
        })
    }

    // XỬ LÝ KẾT QUẢ TRẢ VỀ TỪ FACEBOOK (QUAN TRỌNG)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun logIn() {
        dialog.show()
        // Cập nhật giá trị vào ViewModel
        mModel.email = getBindingData().editTextEmail.text.toString().trim()
        mModel.password = getBindingData().editTextPassword.text.toString().trim()

        if (mModel.email.isEmpty() || mModel.password.isEmpty()) {
            dialog.dismiss()
            // SỬ DỤNG STRING RESOURCE
            getBindingData().tvThongBaoDangNhap.text = getString(R.string.input_required)
            return
        } else {
            mModel.onLogIn()
        }
    }

    override fun getBindingData() = mBinding as FragmentLoginBinding

    override fun getViewModel(): Class<LoginViewModel> {
        return LoginViewModel::class.java
    }

    override fun error(id: String, error: Throwable) {
        dialog.dismiss()
        showMessage(error.message ?: "Lỗi không xác định")
    }

    private fun getFragmentRegiter() {
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        // THÊM CUSTOM ANIMATION (Giả định bạn đã tạo các file anim)
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        fragmentTransaction.replace(R.id.fragmentLogin1, RegisterFragment())
        fragmentTransaction.addToBackStack(RegisterFragment.TAG)
        fragmentTransaction.commit()
    }

    private fun getLogInError() {
        dialog.dismiss()
        showMessage(getString(R.string.login_failed_message))
        getBindingData().tvThongBaoDangNhap.text = getString(R.string.login_error_credentials)
    }

    private fun getLogInSuccess() {
        dialog.dismiss()
        showMessage(getString(R.string.login_success_message))

        mModel.getUser()?.let { user ->
            val targetActivity = when (user.permission) {
                0 -> EmployerMainActivity::class.java
                1 -> CandidateMainActivity::class.java
                else -> return
            }

            val intent = Intent(requireContext(), targetActivity) // Dùng requireContext()
            val bundle = Bundle().apply {
                putSerializable("user", user)
            }
            intent.putExtras(bundle)
            startActivity(intent)

            setLocate()
        }
    }

    private fun setLocate() {
        val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(saveInformation, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        if (getBindingData().cbSave.isChecked) {
            editor.putString("Email", getBindingData().editTextEmail.text.toString().trim())
            editor.putString("Password", getBindingData().editTextPassword.text.toString().trim())
            editor.putBoolean("Save", true)
        } else {
            editor.remove("Email")
            editor.remove("Password")
            editor.remove("Save")
        }
        editor.apply()
    }

    override fun onResume() {
        super.onResume()
        if (isAdded) {
            val sharedPreferences: SharedPreferences = requireActivity().getSharedPreferences(saveInformation, Context.MODE_PRIVATE)
            val save: Boolean = sharedPreferences.getBoolean("Save", false)

            if (save) {
                // Dùng toán tử Elvis an toàn
                val emailResume: String = sharedPreferences.getString("Email", "") ?: ""
                val passwordResume: String = sharedPreferences.getString("Password", "") ?: ""

                getBindingData().editTextEmail.setText(emailResume)
                getBindingData().editTextPassword.setText(passwordResume)
                getBindingData().cbSave.isChecked = true
            }
        }
    }
}