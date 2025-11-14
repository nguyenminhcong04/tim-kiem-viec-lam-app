package com.example.apptuyendungvieclam.ui.candidate.list_job

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

class ListJobSearchViewModel@Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<ListJobCallBack>(appDatabase, interactCommon, scheduler)  {

    private var listJob : ArrayList<Job>? = null
    private var listSkill : ArrayList<Skill>? = null
    private var listQuestion : ArrayList<Question>? = null
    private lateinit var userJob : User
    private lateinit var company: Company
    private var sqLiteHelper : SQLiteHelper? = null
    private var active : Int? = null

    companion object

    fun getJobSearchDataFromDB(context: Context,text: String){
        listJob = ArrayList()
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val dataJob = sqLiteHelper!!.GetData("SELECT * FROM Job4 WHERE status = '1' AND JobName LIKE '%$text%'")
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
    fun getListJobSearch() : ArrayList<Job>{
        return listJob!!
    }
    fun checkActive(context: Context,userId : String) : Boolean{
        val sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val cursor = sqLiteHelper.GetData("SELECT * FROM UserActive WHERE IdUser = '$userId'")
        while (cursor.moveToNext()) {
            active = cursor.getInt(2)
        }
        if(active == 1){
            return true
        }else
            return false
    }
}