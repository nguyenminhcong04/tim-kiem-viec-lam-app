package com.example.apptuyendungvieclam.ui.employer.create_job.create_status


import android.widget.Toast
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.databinding.FragmentSetStatusJobBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.employer.job.my_job.MyJobFragment

class CreateStatusFragment(var idJob : String?,private var user: User?) : BaseMvvmFragment<CreateStatusCallBack,CreateStatusViewModel>(),CreateStatusCallBack{

    private var status : Int = 0

    override fun initComponents() {
        getBindingData().createStatusViewModel = mModel
        mModel.idJob = idJob!!
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                CreateStatusViewModel.CLICK_DONE -> onClickDone()
                CreateStatusViewModel.SET_STATUS_SUCCESS -> onSetStatusSuccess()
            }
        }
    }

    override fun getLayoutMain(): Int {
        return R.layout.fragment_set_status_job
    }

    override fun setEvents() {

    }

    override fun getBindingData() = mBinding as FragmentSetStatusJobBinding

    override fun getViewModel(): Class<CreateStatusViewModel> {
        return CreateStatusViewModel::class.java
    }
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }
    private fun onClickDone(){
        if(getBindingData().rdPublic.isChecked){
            status = 1
        }else if(getBindingData().rdPrivate.isChecked){
            status = 2
        }
        mModel.setStatus(requireContext(),status)
    }
    private fun onSetStatusSuccess(){
        Toast.makeText(context,"Tạo thành công",Toast.LENGTH_SHORT).show()
        val fragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, MyJobFragment(user))
        fragmentTransaction.addToBackStack(MyJobFragment.TAG)
        fragmentTransaction.commit()
    }
    companion object{
        val TAG = CreateStatusFragment::class.java.name
    }
}