package com.example.apptuyendungvieclam.ui.candidate


import androidx.fragment.app.Fragment
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.databinding.ActivityCandidateBinding
import com.example.apptuyendungvieclam.ui.base.activity.BaseMVVMActivity
import com.example.apptuyendungvieclam.ui.candidate.list_job.ListJobSearchFragment
import com.example.apptuyendungvieclam.ui.account.ProfileFragment

class CandidateMainActivity : BaseMVVMActivity<CandidateMainCallBack,CandidateMainModelView>(),CandidateMainCallBack {

    private var backPressTime: Long = 0
    private var user : User? = null

    override fun getLayoutMain(): Int {
        return R.layout.activity_candidate
    }

    override fun setEvents() {

    }

    override fun initComponents() {
        // Cơ chế: INTENT (Nhận dữ liệu)
        // Nhận User từ LoginFragment
        user = intent.getSerializableExtra("user") as User?
        getBindingData().candidateMainViewModel = mModel
        // Khởi tạo Fragment đầu tiên
        getFragmet(ListJobSearchFragment(user))
        onClickMenu()
    }

    override fun getBindingData() = mBinding as ActivityCandidateBinding

    override fun getViewModel(): Class<CandidateMainModelView> {
        return CandidateMainModelView::class.java
    }
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }
//    override fun onBackPressed() {
//        if (backPressTime + 2000 > System.currentTimeMillis()) {
//            finishAffinity()
//            System.exit(0)
//            return
//        } else {
//            Toast.makeText(this, "Nhấn 2 lần liên tiếp để throat app", Toast.LENGTH_SHORT).show()
//        }
//        backPressTime = System.currentTimeMillis()
//    }
    private fun onClickMenu(){
        getBindingData().bottomNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.myJobFragment2 -> {  // Tab Tìm việc
                    getFragmet(ListJobSearchFragment(user))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profileFragment2 -> {  // Tab Hồ sơ
                    getFragmet(ProfileFragment(user))
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
    // Hàm thực hiện Fragment Transaction
    private fun getFragmet(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Thực hiện thay thế Fragment
        fragmentTransaction.replace(R.id.fragmentMain2,fragment)
        fragmentTransaction.commit()
    }
}