package com.example.apptuyendungvieclam.ui.employer.create_job.create_request

import android.content.Context
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class CreateRequestViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<CreateRequestCallBack>(appDatabase, interactCommon, scheduler){

    lateinit var idJob : String
    private lateinit var sqLiteHelper : SQLiteHelper
    private var experienceList : ArrayList<Skill>? = null
    private var educationList : ArrayList<Skill>? = null
    private var certificationList : ArrayList<Skill>? = null
    private var languageList : ArrayList<Skill>? = null

    companion object{
        const val ADD_EXPERIENCE = 1
        const val ADD_EDUCATION = 2
        const val ADD_CERTIFICATION = 3
        const val ADD_LANGUAGE = 4
        const val ADD_EXPERIENCE_SUCCESS = 5
        const val ADD_EDUCATION_SUCCESS = 6
        const val ADD_CERTIFICATION_SUCCESS = 7
        const val ADD_LANGUAGE_SUCCESS = 8
        const val DELETE_EXPERIENCE_SUCCESS = 9
        const val DELETE_EDUCATION_SUCCESS = 10
        const val DELETE_CERTIFICATION_SUCCESS = 11
        const val DELETE_LANGUAGE_SUCCESS = 12
        const val CLICK_NEXT = 13
    }

    fun onClickNext(){
        uiEventLiveData.value = CLICK_NEXT
    }
    fun onClickAddExperience(){
        uiEventLiveData.value = ADD_EXPERIENCE
    }
    fun onClickAddEducation(){
        uiEventLiveData.value = ADD_EDUCATION
    }
    fun onClickAddCertification(){
        uiEventLiveData.value = ADD_CERTIFICATION
    }
    fun onClickAddLanguage(){
        uiEventLiveData.value = ADD_LANGUAGE
    }

    fun addExperience(edExperience: String?,context: Context?){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("INSERT INTO Skill1 VALUES(null,'$edExperience','1','$idJob')")
        uiEventLiveData.value = ADD_EXPERIENCE_SUCCESS
    }
    fun addEducation(edEducation: String?,context: Context?){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("INSERT INTO Skill1 VALUES(null,'$edEducation','2','$idJob')")
        uiEventLiveData.value = ADD_EDUCATION_SUCCESS
    }
    fun addCertification(edCertification: String?,context: Context?){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("INSERT INTO Skill1 VALUES(null,'$edCertification','3','$idJob')")
        uiEventLiveData.value = ADD_CERTIFICATION_SUCCESS
    }
    fun addLanguage(edLanguage: String?,context: Context?){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("INSERT INTO Skill1 VALUES(null,'$edLanguage','4','$idJob')")
        uiEventLiveData.value = ADD_LANGUAGE_SUCCESS
    }

    //get List Experience
    fun getExperience(context: Context?){
        experienceList = ArrayList()
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM Skill1 WHERE IdJob = '$idJob' AND type = '1'")
        while (data.moveToNext()) {
            val id = data.getInt(0)
            val name = data.getString(1)
            val type = data.getInt(2)
            val idJob = data.getString(3)
            val skill = Skill(id,name,type,idJob)
            experienceList!!.add(skill)
        }
    }

    //get List Education
    fun getEducation(context: Context?){
        educationList = ArrayList()
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM Skill1 WHERE IdJob = '$idJob' AND type = '2'")
        while (data.moveToNext()) {
            val id = data.getInt(0)
            val name = data.getString(1)
            val type = data.getInt(2)
            val idJob = data.getString(3)
            val skill = Skill(id,name,type,idJob)
            educationList!!.add(skill)
        }
    }

    //get List Certification
    fun getCertification(context: Context?){
        certificationList = ArrayList()
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM Skill1 WHERE IdJob = '$idJob' AND type = '3'")
        while (data.moveToNext()) {
            val id = data.getInt(0)
            val name = data.getString(1)
            val type = data.getInt(2)
            val idJob = data.getString(3)
            val skill = Skill(id,name,type,idJob)
            certificationList!!.add(skill)
        }
    }

    // get List Language
    fun getLanguage(context: Context?){
        languageList = ArrayList()
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM Skill1 WHERE IdJob = '$idJob' AND type = '4'")
        while (data.moveToNext()) {
            val id = data.getInt(0)
            val name = data.getString(1)
            val type = data.getInt(2)
            val idJob = data.getString(3)
            val skill = Skill(id,name,type,idJob)
            languageList!!.add(skill)
        }
    }

    fun getListExperience() : ArrayList<Skill>{
        return experienceList!!
    }
    fun getListEducation() : ArrayList<Skill>{
        return educationList!!
    }
    fun getListCertification() : ArrayList<Skill>{
        return certificationList!!
    }
    fun getListLanguage() : ArrayList<Skill>{
        return languageList!!
    }

    fun deleteItem(skill : Skill,context: Context?,type: Int){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("DELETE FROM Skill1 WHERE IdJob = '$idJob' AND Id = '${skill.id}'")
        if(type == 1){
            uiEventLiveData.value = DELETE_EXPERIENCE_SUCCESS
        }else if(type == 2){
            uiEventLiveData.value = DELETE_EDUCATION_SUCCESS
        }else if(type == 3){
            uiEventLiveData.value = DELETE_CERTIFICATION_SUCCESS
        }else if(type == 4){
            uiEventLiveData.value = DELETE_LANGUAGE_SUCCESS
        }
    }
}