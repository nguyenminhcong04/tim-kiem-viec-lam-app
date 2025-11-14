package com.example.apptuyendungvieclam.ui.account.update_skill

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import com.example.apptuyendungvieclam.databinding.FragmentUpdateSkillBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.employer.create_job.create_request.SkillAdapter

class UpdateSkillFragment(private var user: User?) :
    BaseMvvmFragment<UpdateSkillCallBack, UpdateSkillViewModel>(),
    UpdateSkillCallBack,
    SkillAdapter.ISkillAdapter {

    private var edExperience: String? = null
    private var edEducation: String? = null
    private var edCertification: String? = null
    private var edLanguage: String? = null

    override fun setEvents() {
        // chưa xử lý sự kiện trực tiếp ở đây
    }

    override fun initComponents() {
        getBindingData().updateSkillViewModel = mModel
        mModel.idAccount = user?.idAccount ?: ""

        // Quan sát sự kiện từ ViewModel
        mModel.uiEventLiveData.observe(this) {
            when (it) {
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                UpdateSkillViewModel.ADD_EXPERIENCE_2 -> onAddExperience()
                UpdateSkillViewModel.ADD_EXPERIENCE_SUCCESS_2 -> onAddExperienceSuccess()
                UpdateSkillViewModel.ADD_EDUCATION_2 -> onAddEducation()
                UpdateSkillViewModel.ADD_EDUCATION_SUCCESS_2 -> onAddEducationSuccess()
                UpdateSkillViewModel.ADD_CERTIFICATION_2 -> onAddCertification()
                UpdateSkillViewModel.ADD_CERTIFICATION_SUCCESS_2 -> onAddCertificationSuccess()
                UpdateSkillViewModel.ADD_LANGUAGE_2 -> onAddLanguage()
                UpdateSkillViewModel.ADD_LANGUAGE_SUCCESS_2 -> onAddLanguageSuccess()
                UpdateSkillViewModel.DELETE_EXPERIENCE_SUCCESS_2 -> onDeleteExperienceSuccess()
                UpdateSkillViewModel.DELETE_EDUCATION_SUCCESS_2 -> onDeleteEducationSuccess()
                UpdateSkillViewModel.DELETE_CERTIFICATION_SUCCESS_2 -> onDeleteCertificationSuccess()
                UpdateSkillViewModel.DELETE_LANGUAGE_SUCCESS_2 -> onDeleteLanguageSuccess()
                UpdateSkillViewModel.CLICK_UPDATE -> upDateSuccess()
            }
        }

        // Load dữ liệu & setup RecyclerView
        mModel.getExperience(context)
        initRecyclerViewExperience()

        mModel.getEducation(context)
        initRecyclerViewEducation()

        mModel.getCertification(context)
        initRecyclerViewCertification()

        mModel.getLanguage(context)
        initRecyclerViewLanguage()
    }

    override fun getBindingData() = mBinding as FragmentUpdateSkillBinding

    override fun getLayoutMain(): Int = R.layout.fragment_update_skill

    override fun getViewModel(): Class<UpdateSkillViewModel> = UpdateSkillViewModel::class.java

    override fun error(id: String, error: Throwable) {
        showMessage(error.message ?: "Có lỗi xảy ra")
    }

    companion object {
        val TAG: String = UpdateSkillFragment::class.java.name

        fun newInstance(user: User): UpdateSkillFragment {
            return UpdateSkillFragment(user)
        }
    }

    private fun upDateSuccess() {
        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
        requireActivity().supportFragmentManager.popBackStack()
    }

    // Đếm số lượng item
    override fun count(type: Int): Int {
        return when (type) {
            1 -> mModel.getListExperience().size
            2 -> mModel.getListEducation().size
            3 -> mModel.getListCertification().size
            4 -> mModel.getListLanguage().size
            else -> 0
        }
    }

    // Lấy skill theo vị trí
    override fun getSkill(position: Int, type: Int): Skill {
        return when (type) {
            1 -> mModel.getListExperience()[position]
            2 -> mModel.getListEducation()[position]
            3 -> mModel.getListCertification()[position]
            4 -> mModel.getListLanguage()[position]
            else -> throw IllegalArgumentException("Loại skill không hợp lệ")
        }
    }

    // Xóa skill
    override fun onClickDeleteSkill(position: Int, type: Int) {
        when (type) {
            1 -> mModel.deleteItem(mModel.getListExperience()[position], context, 1)
            2 -> mModel.deleteItem(mModel.getListEducation()[position], context, 2)
            3 -> mModel.deleteItem(mModel.getListCertification()[position], context, 3)
            4 -> mModel.deleteItem(mModel.getListLanguage()[position], context, 4)
        }
    }

    // ------------------- ADD ----------------------
    // Thao tác INSERT (Thêm)
    private fun onAddExperience() {
        edExperience = getBindingData().edExperience.text.toString().trim()
        if (edExperience.isNullOrEmpty()) {
            Toast.makeText(context, "Nhập dữ liệu", Toast.LENGTH_SHORT).show()
        } else {
            mModel.addExperience(edExperience, context)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onAddExperienceSuccess() {
        // ... (Thông báo thành công)
        Toast.makeText(context, "Thêm kỹ năng thành công", Toast.LENGTH_SHORT).show()
        getBindingData().edExperience.setText("")
        mModel.getExperience(context)
        getBindingData().rcvExperience.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerViewExperience() {
        val experienceAdapter = SkillAdapter(this, 1, 1)
        getBindingData().rcvExperience.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvExperience.adapter = experienceAdapter
    }

    private fun onAddEducation() {
        edEducation = getBindingData().edEducation.text.toString().trim()
        if (edEducation.isNullOrEmpty()) {
            Toast.makeText(context, "Nhập dữ liệu", Toast.LENGTH_SHORT).show()
        } else {
            mModel.addEducation(edEducation, context)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onAddEducationSuccess() {
        Toast.makeText(context, "Thêm học vấn thành công", Toast.LENGTH_SHORT).show()
        getBindingData().edEducation.setText("")
        mModel.getEducation(context)
        getBindingData().rcvEducation.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerViewEducation() {
        val educationAdapter = SkillAdapter(this, 2, 1)
        getBindingData().rcvEducation.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvEducation.adapter = educationAdapter
    }

    private fun onAddCertification() {
        edCertification = getBindingData().edCertification.text.toString().trim()
        if (edCertification.isNullOrEmpty()) {
            Toast.makeText(context, "Nhập dữ liệu", Toast.LENGTH_SHORT).show()
        } else {
            mModel.addCertification(edCertification, context)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onAddCertificationSuccess() {
        Toast.makeText(context, "Thêm chứng chỉ thành công", Toast.LENGTH_SHORT).show()
        getBindingData().edCertification.setText("")
        mModel.getCertification(context)
        getBindingData().revCertification.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerViewCertification() {
        val certificationAdapter = SkillAdapter(this, 3, 1)
        getBindingData().revCertification.layoutManager = LinearLayoutManager(context)
        getBindingData().revCertification.adapter = certificationAdapter
    }

    private fun onAddLanguage() {
        edLanguage = getBindingData().edLanguage.text.toString().trim()
        if (edLanguage.isNullOrEmpty()) {
            Toast.makeText(context, "Nhập dữ liệu", Toast.LENGTH_SHORT).show()
        } else {
            mModel.addLanguage(edLanguage, context)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onAddLanguageSuccess() {
        Toast.makeText(context, "Thêm ngôn ngữ thành công", Toast.LENGTH_SHORT).show()
        getBindingData().edLanguage.setText("")
        mModel.getLanguage(context)
        getBindingData().rcvLanguage.adapter?.notifyDataSetChanged()
    }

    private fun initRecyclerViewLanguage() {
        val languageAdapter = SkillAdapter(this, 4, 1)
        getBindingData().rcvLanguage.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvLanguage.adapter = languageAdapter
    }

    // ------------------- DELETE ----------------------
    @SuppressLint("NotifyDataSetChanged")
    fun onDeleteExperienceSuccess() {
        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
        mModel.getExperience(context)
        getBindingData().rcvExperience.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onDeleteEducationSuccess() {
        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
        mModel.getEducation(context)
        getBindingData().rcvEducation.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onDeleteCertificationSuccess() {
        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
        mModel.getCertification(context)
        getBindingData().revCertification.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onDeleteLanguageSuccess() {
        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
        mModel.getLanguage(context)
        getBindingData().rcvLanguage.adapter?.notifyDataSetChanged()
    }
}
