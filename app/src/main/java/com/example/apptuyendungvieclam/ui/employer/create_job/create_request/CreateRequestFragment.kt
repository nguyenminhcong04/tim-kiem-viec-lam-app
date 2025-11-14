package com.example.apptuyendungvieclam.ui.employer.create_job.create_request

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import com.example.apptuyendungvieclam.databinding.FragmentCreateRequestBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.employer.create_job.create_question.CreateQuestionFragment


class CreateRequestFragment(var idJob : String?,private var user: User?,private var type : Int) :
    BaseMvvmFragment<CreateRequestCallBack,CreateRequestViewModel>(),CreateRequestCallBack ,
    SkillAdapter.ISkillAdapter{

    private var edExperience : String? = null
    private var edEducation : String? = null
    private var edCertification : String? = null
    private var edLanguage : String? = null

    override fun initComponents() {
            getBindingData().createRequestViewModel = mModel
            mModel.idJob = idJob!!
            mModel.uiEventLiveData.observe(this){
                when(it){
                    BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                    CreateRequestViewModel.ADD_EXPERIENCE -> onAddExperience()
                    CreateRequestViewModel.ADD_EXPERIENCE_SUCCESS -> onAddExperienceSuccess()
                    CreateRequestViewModel.ADD_EDUCATION -> onAddEducation()
                    CreateRequestViewModel.ADD_EDUCATION_SUCCESS -> onAddEducationSuccess()
                    CreateRequestViewModel.ADD_CERTIFICATION -> onAddCertification()
                    CreateRequestViewModel.ADD_CERTIFICATION_SUCCESS -> onAddCertificationSuccess()
                    CreateRequestViewModel.ADD_LANGUAGE -> onAddLanguage()
                    CreateRequestViewModel.ADD_LANGUAGE_SUCCESS -> onAddLanguageSuccess()
                    CreateRequestViewModel.CLICK_NEXT -> onClickNextToQuestion()
                    CreateRequestViewModel.DELETE_EXPERIENCE_SUCCESS -> onDeleteExperienceSuccess()
                    CreateRequestViewModel.DELETE_EDUCATION_SUCCESS -> onDeleteEducationSuccess()
                    CreateRequestViewModel.DELETE_CERTIFICATION_SUCCESS -> onDeleteCertificationSuccess()
                    CreateRequestViewModel.DELETE_LANGUAGE_SUCCESS -> onDeleteLanguageSuccess()
                }
            }

        mModel.getExperience(context)
        initRecyclerViewExperience()

        mModel.getEducation(context)
        initRecyclerViewEducation()

        mModel.getCertification(context)
        initRecyclerViewCertification()

        mModel.getLanguage(context)
        initRecyclerViewLanguage()

    }

    override fun getLayoutMain(): Int {
        return R.layout.fragment_create_request
    }

    override fun setEvents() {

    }

    override fun getBindingData() = mBinding as FragmentCreateRequestBinding

    override fun getViewModel(): Class<CreateRequestViewModel> {
        return CreateRequestViewModel::class.java
    }
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }
    companion object{
        val TAG = CreateRequestFragment::class.java.name
    }

    private fun onClickNextToQuestion(){
        val fragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain,CreateQuestionFragment(idJob,user,type))
        fragmentTransaction.addToBackStack(CreateQuestionFragment.TAG)
        fragmentTransaction.commit()
    }
    // add Experience
    private fun onAddExperience(){
        edExperience = getBindingData().edExperience.text.toString().trim()
        if(edExperience!!.isEmpty()){
            Toast.makeText(context,"Nhập dữ liệu",Toast.LENGTH_SHORT).show()
        }else{
            mModel.addExperience(edExperience,context)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun onAddExperienceSuccess(){
        getBindingData().edExperience.setText("")
        mModel.getExperience(context)
        Log.e(mModel.getListExperience().size.toString(),"Test")
        getBindingData().rcvExperience.adapter!!.notifyDataSetChanged()
    }
    private fun initRecyclerViewExperience(){
        val experienceAdapter = SkillAdapter(this,1,1)
        getBindingData().rcvExperience.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvExperience.adapter = experienceAdapter
    }

    // add Education
    private fun onAddEducation(){
        edEducation = getBindingData().edEducation.text.toString().trim()
        if(edEducation!!.isEmpty()){
            Toast.makeText(context,"Nhập dữ liệu",Toast.LENGTH_SHORT).show()
        }else{
            mModel.addEducation(edEducation,context)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onAddEducationSuccess(){
        getBindingData().edEducation.setText("")
        mModel.getEducation(context)
        Log.e(mModel.getListEducation().size.toString(),"Test")
        getBindingData().rcvEducation.adapter!!.notifyDataSetChanged()
    }
    private fun initRecyclerViewEducation(){
        val educationAdapter = SkillAdapter(this,2,1)
        getBindingData().rcvEducation.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvEducation.adapter = educationAdapter
    }

    // add Certification
    private fun onAddCertification(){
        edCertification = getBindingData().edCertification.text.toString().trim()
        if(edCertification!!.isEmpty()){
            Toast.makeText(context,"Nhập dữ liệu",Toast.LENGTH_SHORT).show()
        }else{
            mModel.addCertification(edCertification,context)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun onAddCertificationSuccess(){
        getBindingData().edCertification.setText("")
        mModel.getCertification(context)
        Log.e(mModel.getListCertification().size.toString(),"Test")
        getBindingData().revCertification.adapter!!.notifyDataSetChanged()
    }
    private fun initRecyclerViewCertification(){
        val certificationAdapter = SkillAdapter(this,3,1)
        getBindingData().revCertification.layoutManager = LinearLayoutManager(context)
        getBindingData().revCertification.adapter = certificationAdapter
    }

    // add Language
    private fun onAddLanguage(){
        edLanguage = getBindingData().edLanguage.text.toString().trim()
        if(edLanguage!!.isEmpty()){
            Toast.makeText(context,"Nhập dữ liệu",Toast.LENGTH_SHORT).show()
        }else{
            mModel.addLanguage(edLanguage,context)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun onAddLanguageSuccess(){
        getBindingData().edLanguage.setText("")
        mModel.getLanguage(context)
        Log.e(mModel.getListLanguage().size.toString(),"Test")
        getBindingData().rcvLanguage.adapter!!.notifyDataSetChanged()
    }
    private fun initRecyclerViewLanguage(){
        val languageAdapter = SkillAdapter(this,4,1)
        getBindingData().rcvLanguage.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvLanguage.adapter = languageAdapter
    }

    override fun count(type : Int): Int {
        when(type){
            1 -> {
                return mModel.getListExperience().size
            }
            2 -> {
                return mModel.getListEducation().size
            }
            3 -> {
                return mModel.getListCertification().size
            }
            4 -> {
                return mModel.getListLanguage().size
            }
            else -> 0
        }
        return 0
    }

    override fun getSkill(position: Int,type : Int): Skill {
        when(type){
             1 -> {
                 return mModel.getListExperience()[position]
             }
             2 -> {
                 return mModel.getListEducation()[position]
             }
             3 -> {
                 return mModel.getListCertification()[position]
             }
             4 -> {
                 return mModel.getListLanguage()[position]
             }
             else -> 0
        }
        return mModel.getListExperience()[position]
    }

    override fun onClickDeleteSkill(position: Int,type : Int) {
        when(type){
            1 -> {
                val skill = mModel.getListExperience()[position]
                mModel.deleteItem(skill,context,1)
            }
            2 -> {
                val skill = mModel.getListEducation()[position]
                mModel.deleteItem(skill,context,2)
            }
            3 -> {
                val skill = mModel.getListCertification()[position]
                mModel.deleteItem(skill,context,3)
            }
            4 -> {
                val skill = mModel.getListLanguage()[position]
                mModel.deleteItem(skill,context,4)
            }
            else -> 0
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun onDeleteExperienceSuccess(){
        Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show()
        mModel.getExperience(context)
        getBindingData().rcvExperience.adapter!!.notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun onDeleteEducationSuccess(){
        Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show()
        mModel.getEducation(context)
        getBindingData().rcvEducation.adapter!!.notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun onDeleteCertificationSuccess(){
        Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show()
        mModel.getCertification(context)
        getBindingData().revCertification.adapter!!.notifyDataSetChanged()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun onDeleteLanguageSuccess(){
        Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show()
        mModel.getLanguage(context)
        getBindingData().rcvLanguage.adapter!!.notifyDataSetChanged()
    }
}