package com.example.apptuyendungvieclam.ui.employer

import androidx.fragment.app.Fragment
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.databinding.ActivityEmployerMainBinding
import com.example.apptuyendungvieclam.ui.base.activity.BaseMVVMActivity
import com.example.apptuyendungvieclam.ui.employer.job.my_job.MyJobFragment
import com.example.apptuyendungvieclam.ui.noticification.NoticificationFragment
import com.example.apptuyendungvieclam.ui.account.ProfileFragment

class EmployerMainActivity : BaseMVVMActivity<EmployerMainCallBack,EmployerMainViewModel>(),EmployerMainCallBack {

    private var backPressTime: Long = 0
    private var user : User? = null

    override fun getLayoutMain(): Int {
       return R.layout.activity_employer_main;
    }

    override fun setEvents() {

    }

    override fun initComponents() {
        // Cơ chế: INTENT (Nhận dữ liệu)
        user = intent.getSerializableExtra("user") as User? // Nhận User từ LoginFragment
        getBindingData().employerMainViewModel = mModel
        // Khởi tạo Fragment đầu tiên và truyền dữ liệu
        getFragmet(MyJobFragment(user))
        // Gán listener cho Bottom Navigation
        onClickMenu()
    }

    override fun getBindingData() = mBinding as ActivityEmployerMainBinding

    override fun getViewModel(): Class<EmployerMainViewModel> {
        return EmployerMainViewModel::class.java
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
//            Toast.makeText(this, "Nhấn 2 lần liên tiếp để thoát app", Toast.LENGTH_SHORT).show()
//        }
//        backPressTime = System.currentTimeMillis()
//    }

// Cơ chế: FRAGMENT TRANSACTION (Thay thế Fragment)
    private fun getFragmet(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // R.id.fragmentMain là container
        fragmentTransaction.replace(R.id.fragmentMain,fragment)
        // Không dùng addToBackStack cho các tab chính
        fragmentTransaction.commit()
    }
    // Xử lý sự kiện Bottom Navigation Bar
    private fun onClickMenu(){
        // Giả định getBindingData().bottomNav là BottomNavigationView
        getBindingData().bottomNav.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.myJobFragment -> {
                    getFragmet(MyJobFragment(user))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.profileFragment -> {
                    getFragmet(ProfileFragment(user))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.notificationFragment -> {
                    getFragmet(NoticificationFragment(user))
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
}