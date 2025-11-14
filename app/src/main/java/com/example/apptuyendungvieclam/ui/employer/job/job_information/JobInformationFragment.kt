package com.example.apptuyendungvieclam.ui.employer.job.job_information


import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.Job
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import com.example.apptuyendungvieclam.databinding.FragmentJobInformationBinding
import com.example.apptuyendungvieclam.ui.base.fragment.BaseMvvmFragment
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import com.example.apptuyendungvieclam.ui.candidate.answer.AnswerFragment
import com.example.apptuyendungvieclam.ui.employer.create_job.create_request.CreateRequestFragment
import com.example.apptuyendungvieclam.ui.employer.create_job.create_request.SkillAdapter

class JobInformationFragment(val user: User) : BaseMvvmFragment<JobInformationCallBack, JobInformationViewModel>(),
    JobInformationCallBack,SkillAdapter.ISkillAdapter {

    private lateinit var job : Job
    private lateinit var listSkill : ArrayList<Skill>
    private lateinit var listExperience : ArrayList<Skill>
    private lateinit var listEducation : ArrayList<Skill>
    private lateinit var listCertification : ArrayList<Skill>
    private lateinit var listLanguage : ArrayList<Skill>

    override fun initComponents() {
        getBindingData().jobInforViewModel = mModel
        job = arguments!!.getSerializable("job") as Job
        mModel.uiEventLiveData.observe(this){
            when(it){
                BaseViewModel.FINISH_ACTIVITY -> finishActivity()
                JobInformationViewModel.ON_CLICK_APPLY -> onClickApply()
                JobInformationViewModel.ON_CLICK_UPDATE -> onClickUpdate()
            }
        }
        getListSkill()
        setView()
        initRecyclerViewExperience()
        initRecyclerViewEducation()
        initRecyclerViewCertification()
        initRecyclerViewLanguage()
    }

    private fun setView(){
        getBindingData().jobName.text = job.jobName
        getBindingData().jobDescription.text = job.description
        getBindingData().companyName.text = job.company!!.companyName
        Glide.with(context!!).load(job.company!!.companyAvatar)
            .placeholder(R.drawable.img_company_load)
            .error(R.drawable.img_company_false)
            .into(getBindingData().logoCompany)
        getBindingData().jobRight.text = job.right
        getBindingData().jobAmount.text = ""+job.amount
        getBindingData().tvEmployName.text = "Anh / chị : " + job.employer!!.name
        getBindingData().tvEmployEmail.text = "Email : " + job.employer!!.email
        getBindingData().tvEmployPhone.text = "Số điện thoại : " + job.employer!!.phone
        if(user.permission == 0){
            getBindingData().btnApply.visibility = View.GONE
            getBindingData().btnUpdate.visibility = View.VISIBLE
        }else if(user.permission == 1){
            getBindingData().btnApply.visibility = View.VISIBLE
            getBindingData().btnUpdate.visibility = View.GONE
        }
    }

    private fun getListSkill(){
        listSkill = ArrayList()
        listExperience = ArrayList()
        listEducation = ArrayList()
        listCertification = ArrayList()
        listLanguage = ArrayList()
        listSkill = job.listSkill!!
        for (skill in listSkill){
            if(skill.type == 1){
                listExperience.add(skill)
            }else if(skill.type == 2){
                listEducation.add(skill)
            }else if(skill.type == 3){
                listCertification.add(skill)
            }else if(skill.type == 4){
                listLanguage.add(skill)
            }
        }
    }

    private fun onClickApply(){
        // Cơ chế: FRAGMENT TRANSACTION (Chuyển trang)
        val fragmentTransaction = requireFragmentManager().beginTransaction()
        // Trang đích: AnswerFragment (Màn hình Nộp đơn)
        fragmentTransaction.replace(R.id.fragmentMain2, AnswerFragment(job,user))
        fragmentTransaction.addToBackStack(AnswerFragment.TAG)
        fragmentTransaction.commit()
    }

    private fun onClickUpdate(){
        val fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentMain, CreateRequestFragment(job.codeJob,user,2))
        fragmentTransaction.addToBackStack(CreateRequestFragment.TAG)
        fragmentTransaction.commit()
    }

    override fun getLayoutMain(): Int {
        return R.layout.fragment_job_information
    }

    override fun setEvents() {

    }

    override fun getBindingData() = mBinding as FragmentJobInformationBinding

    override fun getViewModel(): Class<JobInformationViewModel> {
        return JobInformationViewModel::class.java
    }
    override fun error(id: String, error: Throwable) {
        showMessage(error.message!!)
    }
    companion object {
        val TAG: String = JobInformationFragment::class.java.name
    }

    override fun count(type: Int): Int {
        when(type){
            1 -> {
                return listExperience.size
            }
            2 -> {
                return listEducation.size
            }
            3 -> {
                return listCertification.size
            }
            4 -> {
                return listLanguage.size
            }
            else -> 0
        }
        return 0
    }

    override fun getSkill(position: Int, type: Int): Skill {
        when(type){
            1 -> {
                return listExperience[position]
            }
            2 -> {
                return listEducation[position]
            }
            3 -> {
                return listCertification[position]
            }
            4 -> {
                return listLanguage[position]
            }
            else -> 0
        }
        return listExperience[position]
    }

    override fun onClickDeleteSkill(position: Int, type: Int) {

    }
    private fun initRecyclerViewExperience(){
        val experienceAdapter = SkillAdapter(this,1,2)
        getBindingData().rcvInfoExperience.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvInfoExperience.adapter = experienceAdapter
    }
    private fun initRecyclerViewEducation(){
        val educationAdapter = SkillAdapter(this,2,2)
        getBindingData().rcvInfoEducation.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvInfoEducation.adapter = educationAdapter
    }
    private fun initRecyclerViewCertification(){
        val certificationAdapter = SkillAdapter(this,3,2)
        getBindingData().rcvInfoCerticifation.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvInfoCerticifation.adapter = certificationAdapter
    }
    private fun initRecyclerViewLanguage(){
        val languageAdapter = SkillAdapter(this,4,2)
        getBindingData().rcvInfoLanguage.layoutManager = LinearLayoutManager(context)
        getBindingData().rcvInfoLanguage.adapter = languageAdapter
    }
}