package com.example.apptuyendungvieclam.ui.xample.user

import android.annotation.SuppressLint
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.UserExample
import com.example.apptuyendungvieclam.databinding.FragmentUserBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.lang.ref.WeakReference

class UserFragment : BaseMvvmFragment<UserCallBack, UserViewModel>(),UserCallBack, UserAdapter.IUserAdapter {
    override fun getLayoutMain(): Int {
        return R.layout.fragment_user
    }

    override fun setEvents() {

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initComponents() {
        getBindingData().userViewModel = mModel
        mModel.callBack = WeakReference(this)
        mModel.obUsers.observe(this) {
            if (getBindingData().rcListUser.adapter == null) {
                getBindingData().rcListUser.layoutManager = LinearLayoutManager(getBaseActivity())
                getBindingData().rcListUser.adapter = UserAdapter(this@UserFragment)
            } else {
                getBindingData().rcListUser.adapter!!.notifyDataSetChanged()
            }
        }
        mModel.uiEventLiveData.observe(this){
           when(it){
               BaseViewModel.FINISH_ACTIVITY-> finishActivity()
           }
        }
    }

    override fun getBindingData() = mBinding as FragmentUserBinding

    override fun getViewModel(): Class<UserViewModel> {
        return UserViewModel::class.java
    }

    override fun count(): Int {
        if (mModel.obUsers.value == null){
            return 0
        }
        return mModel.obUsers.value!!.size
    }

    override fun getData(position: Int): UserExample {
        return mModel.obUsers.value!!.get(position)
    }

    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
        when (id) {
            UserExample::class.java.name -> {
                mModel.getAllUserOffline()
            }
        }
    }
}