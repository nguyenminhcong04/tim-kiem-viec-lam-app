package com.example.apptuyendungvieclam.ui.employer.job.my_job

import android.content.Context
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.Company
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.Job
import com.example.apptuyendungvieclam.data.model.job.question.Question
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class MyJobViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<MyJobCallBack>(appDatabase, interactCommon, scheduler) {

    private var listJob : ArrayList<Job>? = null
    private var listSkill : ArrayList<Skill>? = null
    private var listQuestion : ArrayList<Question>? = null
    private lateinit var userJob : User
    private lateinit var company: Company
    private var sqLiteHelper : SQLiteHelper? = null

    fun onClickAddJob(){
        uiEventLiveData.value = GO_ADD_JOB
    }
    companion object{
        const val GO_ADD_JOB = 1000
        const val SET_POWER_SUCCESS = 1001
        const val DELETE_SUCCESS = 1002
    }
    fun getJobDataFromDB(context: Context,user : User,text: String){
        listJob = ArrayList()
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val dataJob = sqLiteHelper!!.GetData("SELECT * FROM Job4 WHERE IdEmployer = '${user.idAccount}' AND JobName LIKE '%$text%' ORDER BY Id DESC")
        while (dataJob.moveToNext()){
             val id = dataJob.getInt(0)
             val code = dataJob.getString(1)
             val jobName = dataJob.getString(2)
             val description = dataJob.getString(3)
             val IdEmployer = dataJob.getString(4)
             val IdCompany = dataJob.getInt(5)
             val right = dataJob.getString(6)
             val amount = dataJob.getInt(7)
             val status = dataJob.getInt(8)
            // get data Skill of Job
            listSkill = ArrayList()
            val dataSkill = sqLiteHelper!!.GetData("SELECT * FROM Skill1 WHERE IdJob = '$code'")
            while (dataSkill.moveToNext()) {
                val idSkill = dataSkill.getInt(0)
                val name = dataSkill.getString(1)
                val type = dataSkill.getInt(2)
                val idJob = dataSkill.getString(3)
                listSkill!!.add(Skill(idSkill, name, type, idJob))
            }
            // get data Question of Job
            listQuestion= ArrayList()
            val dataQuestion = sqLiteHelper!!.GetData("SELECT * FROM Question WHERE IdJob = '$code'")
            while (dataQuestion.moveToNext()) {
                val idQuestion = dataQuestion.getInt(0)
                val content = dataQuestion.getString(1)
                val idJob = dataQuestion.getString(2)
                listQuestion!!.add(Question(idQuestion, content, idJob))
            }
            // get data Company of Job
            val dataCompany = sqLiteHelper!!.GetData("SELECT * FROM Company WHERE Id = '$IdCompany'")
            while (dataCompany.moveToNext()) {
                val idCompany = dataCompany.getInt(0)
                val companyName = dataCompany.getString(1)
                val companyAvatar = dataCompany.getString(2)
                val companyAddress = dataCompany.getString(3)
                company = Company(idCompany,companyName,companyAvatar,companyAddress)
            }
            // get data User of Job
            val dataUser = sqLiteHelper!!.GetData("SELECT * FROM User WHERE IdAccount = '$IdEmployer'")
            while (dataUser.moveToNext()) {
                val idUser = dataUser.getInt(0)
                val IdAccount = dataUser.getString(1)
                val email = dataUser.getString(2)
                val password = dataUser.getString(3)
                val userName = dataUser.getString(4)
                val age = dataUser.getInt(5)
                val phone = dataUser.getString(6)
                val permission = dataUser.getInt(7)
                val IdCompany = dataUser.getInt(8)
                userJob = User(IdAccount,email,password,userName,age,phone,permission)
            }
            val job = Job(id,code,jobName,description,listSkill,listQuestion,right,amount,status,userJob,company)
            listJob!!.add(job)
        }
    }

    fun getListJob() : ArrayList<Job>{
        return listJob!!
    }

    fun setStatus(job: Job , context: Context){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        if(job.status == 1){
            sqLiteHelper!!.QueryData("UPDATE Job4 SET status = '2' WHERE JobCode ='${job.codeJob}'")
            uiEventLiveData.value = SET_POWER_SUCCESS
        }else if(job.status == 2){
            sqLiteHelper!!.QueryData("UPDATE Job4 SET status = '1' WHERE JobCode ='${job.codeJob}'")
            uiEventLiveData.value = SET_POWER_SUCCESS
        }
    }
    fun deleteJob(job: Job , context: Context){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper!!.QueryData("DELETE FROM Job4 WHERE JobCode ='${job.codeJob}'")
        sqLiteHelper!!.QueryData("DELETE FROM Apply WHERE IdJob ='${job.idJob}'")
        sqLiteHelper!!.QueryData("DELETE FROM Skill1 WHERE IdJob ='${job.codeJob}'")
        sqLiteHelper!!.QueryData("DELETE FROM Question WHERE IdJob ='${job.codeJob}'")
        uiEventLiveData.value = DELETE_SUCCESS
    }
}