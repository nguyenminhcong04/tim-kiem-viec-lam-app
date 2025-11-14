package com.example.apptuyendungvieclam.ui.noticification

import android.content.Context
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.AvatarUser
import com.example.apptuyendungvieclam.data.model.Company
import com.example.apptuyendungvieclam.data.model.NotificationItem
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.Job
import com.example.apptuyendungvieclam.data.model.job.question.Question
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class NotificationViewModel  @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<NotificationCallBack>(appDatabase, interactCommon, scheduler) {

    private var listNotification : ArrayList<NotificationItem>? = null
    private var listSkill : ArrayList<Skill>? = null
    private var listQuestion : ArrayList<Question>? = null
    private lateinit var userJob : User
    private lateinit var company: Company
    private lateinit var candidate : User
    private lateinit var job : Job
    private lateinit var avatarUser : AvatarUser
    fun getDataNotification(employer : User,context: Context){
        listNotification = ArrayList()
        val sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val dataNotification = sqLiteHelper.GetData("SELECT * FROM Apply WHERE IdEmployer = '${employer.idAccount}' ORDER BY Id DESC")
        while (dataNotification.moveToNext()){
            val id = dataNotification.getInt(0)
            val IdCandidate = dataNotification.getString(1)
            val IdJob = dataNotification.getInt(2)
            val IdEmployer = dataNotification.getString(3)

            //get data Job
            val dataJob = sqLiteHelper.GetData("SELECT * FROM Job4 WHERE Id = '$IdJob'")
            while (dataJob.moveToNext()){
                val idJob = dataJob.getInt(0)
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
                val dataSkill = sqLiteHelper.GetData("SELECT * FROM Skill1 WHERE IdJob = '$code'")
                while (dataSkill.moveToNext()) {
                    val idSkill = dataSkill.getInt(0)
                    val name = dataSkill.getString(1)
                    val type = dataSkill.getInt(2)
                    val idJob = dataSkill.getString(3)
                    listSkill!!.add(Skill(idSkill, name, type, idJob))
                }
                // get data Question of Job
                listQuestion= ArrayList()
                val dataQuestion = sqLiteHelper.GetData("SELECT * FROM Question WHERE IdJob = '$code'")
                while (dataQuestion.moveToNext()) {
                    val idQuestion = dataQuestion.getInt(0)
                    val content = dataQuestion.getString(1)
                    val idJob = dataQuestion.getString(2)
                    listQuestion!!.add(Question(idQuestion, content, idJob))
                }
                // get data Company of Job
                val dataCompany = sqLiteHelper.GetData("SELECT * FROM Company WHERE Id = '$IdCompany'")
                while (dataCompany.moveToNext()) {
                    val idCompany = dataCompany.getInt(0)
                    val companyName = dataCompany.getString(1)
                    val companyAvatar = dataCompany.getString(2)
                    val companyAddress = dataCompany.getString(3)
                    company = Company(idCompany,companyName,companyAvatar,companyAddress)
                }
                // get data User of Job
                val dataUser = sqLiteHelper.GetData("SELECT * FROM User WHERE IdAccount = '$IdEmployer'")
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
                job = Job(idJob,code,jobName,description,listSkill,listQuestion,right,amount,status,userJob,company)
            }
            // get infor of candidate
            val dataUser = sqLiteHelper.GetData("SELECT * FROM User WHERE IdAccount = '$IdCandidate'")
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
                candidate = User(IdAccount,email,password,userName,age,phone,permission)
            }
            //get avatar
            val data = sqLiteHelper.GetData("SELECT * FROM UserAvatar WHERE IdUser = '$IdCandidate'")
            while (data.moveToNext()){
                val idAvt= data.getInt(0)
                val idAc = data.getString(1)
                val strAvt = data.getString(2)
                avatarUser = AvatarUser(idAvt,idAc,strAvt)
            }
            listNotification!!.add(NotificationItem(id,candidate,job,avatarUser))
        }
    }
    fun getListNotification() : ArrayList<NotificationItem>{
        return listNotification!!
    }
}