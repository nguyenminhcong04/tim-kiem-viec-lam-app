package com.example.apptuyendungvieclam.ui.account.update_skill

import android.content.Context
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class UpdateSkillViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<UpdateSkillCallBack>(appDatabase, interactCommon, scheduler) {

    lateinit var idAccount: String
    private lateinit var sqLiteHelper: SQLiteHelper

    private var experienceList = arrayListOf<Skill>()
    private var educationList = arrayListOf<Skill>()
    private var certificationList = arrayListOf<Skill>()
    private var languageList = arrayListOf<Skill>()

    companion object {
        const val ADD_EXPERIENCE_2 = 1
        const val ADD_EDUCATION_2 = 2
        const val ADD_CERTIFICATION_2 = 3
        const val ADD_LANGUAGE_2 = 4
        const val ADD_EXPERIENCE_SUCCESS_2 = 5
        const val ADD_EDUCATION_SUCCESS_2 = 6
        const val ADD_CERTIFICATION_SUCCESS_2 = 7
        const val ADD_LANGUAGE_SUCCESS_2 = 8
        const val DELETE_EXPERIENCE_SUCCESS_2 = 9
        const val DELETE_EDUCATION_SUCCESS_2 = 10
        const val DELETE_CERTIFICATION_SUCCESS_2 = 11
        const val DELETE_LANGUAGE_SUCCESS_2 = 12
        const val CLICK_UPDATE = 13
    }

    private fun getHelper(context: Context?): SQLiteHelper {
        if (!::sqLiteHelper.isInitialized) {
            sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        }
        return sqLiteHelper
    }

    fun onClickNext() {
        uiEventLiveData.value = CLICK_UPDATE
    }

    fun onClickAddExperience() {
        uiEventLiveData.value = ADD_EXPERIENCE_2
    }

    fun onClickAddEducation() {
        uiEventLiveData.value = ADD_EDUCATION_2
    }

    fun onClickAddCertification() {
        uiEventLiveData.value = ADD_CERTIFICATION_2
    }

    fun onClickAddLanguage() {
        uiEventLiveData.value = ADD_LANGUAGE_2
    }

    fun addExperience(edExperience: String?, context: Context?) {
        getHelper(context).QueryData(
            "INSERT INTO UserSkill VALUES(null,'$edExperience','1','$idAccount')"
        )
        uiEventLiveData.value = ADD_EXPERIENCE_SUCCESS_2
    }

    fun addEducation(edEducation: String?, context: Context?) {
        getHelper(context).QueryData(
            "INSERT INTO UserSkill VALUES(null,'$edEducation','2','$idAccount')"
        )
        uiEventLiveData.value = ADD_EDUCATION_SUCCESS_2
    }

    fun addCertification(edCertification: String?, context: Context?) {
        getHelper(context).QueryData(
            "INSERT INTO UserSkill VALUES(null,'$edCertification','3','$idAccount')"
        )
        uiEventLiveData.value = ADD_CERTIFICATION_SUCCESS_2
    }

    fun addLanguage(edLanguage: String?, context: Context?) {
        getHelper(context).QueryData(
            "INSERT INTO UserSkill VALUES(null,'$edLanguage','4','$idAccount')"
        )
        uiEventLiveData.value = ADD_LANGUAGE_SUCCESS_2
    }

    // ------------------- GET LIST -------------------
    fun getExperience(context: Context?) {
        experienceList.clear()
        val data = getHelper(context).GetData(
            "SELECT * FROM UserSkill WHERE IdAccount = '$idAccount' AND type = '1'"
        )
        while (data.moveToNext()) {
            val skill = Skill(
                data.getInt(0),
                data.getString(1),
                data.getInt(2),
                data.getString(3)
            )
            experienceList.add(skill)
        }
    }

    fun getEducation(context: Context?) {
        educationList.clear()
        val data = getHelper(context).GetData(
            "SELECT * FROM UserSkill WHERE IdAccount = '$idAccount' AND type = '2'"
        )
        while (data.moveToNext()) {
            val skill = Skill(
                data.getInt(0),
                data.getString(1),
                data.getInt(2),
                data.getString(3)
            )
            educationList.add(skill)
        }
    }

    fun getCertification(context: Context?) {
        certificationList.clear()
        val data = getHelper(context).GetData(
            "SELECT * FROM UserSkill WHERE IdAccount = '$idAccount' AND type = '3'"
        )
        while (data.moveToNext()) {
            val skill = Skill(
                data.getInt(0),
                data.getString(1),
                data.getInt(2),
                data.getString(3)
            )
            certificationList.add(skill)
        }
    }

    fun getLanguage(context: Context?) {
        languageList.clear()
        val data = getHelper(context).GetData(
            "SELECT * FROM UserSkill WHERE IdAccount = '$idAccount' AND type = '4'"
        )
        while (data.moveToNext()) {
            val skill = Skill(
                data.getInt(0),
                data.getString(1),
                data.getInt(2),
                data.getString(3)
            )
            languageList.add(skill)
        }
    }

    // ------------------- RETURN LIST -------------------
    fun getListExperience(): ArrayList<Skill> = experienceList
    fun getListEducation(): ArrayList<Skill> = educationList
    fun getListCertification(): ArrayList<Skill> = certificationList
    fun getListLanguage(): ArrayList<Skill> = languageList

    // ------------------- DELETE -------------------
    fun deleteItem(skill: Skill, context: Context?, type: Int) {
        getHelper(context).QueryData(
            "DELETE FROM UserSkill WHERE IdAccount = '$idAccount' AND Id = '${skill.id}'"
        )

        uiEventLiveData.value = when (type) {
            1 -> DELETE_EXPERIENCE_SUCCESS_2
            2 -> DELETE_EDUCATION_SUCCESS_2
            3 -> DELETE_CERTIFICATION_SUCCESS_2
            4 -> DELETE_LANGUAGE_SUCCESS_2
            else -> null
        }
    }
}
