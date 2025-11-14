package com.example.apptuyendungvieclam.ui.employer.create_job.create_right

import android.widget.Toast
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.databinding.FragmentCreateRightBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.employer.create_job.create_status.CreateStatusFragment

class CreateRightFragment(var idJob : String?,private var user: User?)  : BaseMvvmFragment<CreateRightCallBack,CreateRightViewModel>(),CreateRightCallBack {

    private var edRight : String? = null
    private var edAmount : Int = 0

    override fun initComponents() {
        getBindingData().createRightViewModel = mModel
        mModel.idJob = idJob!!
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                CreateRightViewModel.ON_CLICK_NEXT -> onClickNext()
                CreateRightViewModel.ADD_RIGHT_SUCCESS -> onAddRightSuccess()
            }
        }
    }

    override fun getLayoutMain(): Int {
        return R.layout.fragment_create_right
    }

    override fun setEvents() {

    }

    override fun getBindingData() = mBinding as FragmentCreateRightBinding

    override fun getViewModel(): Class<CreateRightViewModel> {
        return CreateRightViewModel::class.java
    }
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }
    fun onClickNext(){
       edRight = getBindingData().edRight.text.toString().trim()
       val amount = getBindingData().edAmount.text.toString().trim()
       if(amount.isEmpty()){
           Toast.makeText(context,"Vui lòng nhập số lượng",Toast.LENGTH_SHORT).show()
       }else{
           edAmount = amount.toInt()
           mModel.setRightAndAmount(edRight!!,edAmount,requireContext())
       }
    }
    private fun onAddRightSuccess(){
        val fragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, CreateStatusFragment(idJob,user))
        fragmentTransaction.addToBackStack(CreateStatusFragment.TAG)
        fragmentTransaction.commit()
    }
    companion object{
        val TAG = CreateRightFragment::class.java.name
    }

}