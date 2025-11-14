package com.example.apptuyendungvieclam.ui.account.information

import android.content.Context
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.AvatarUser
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class InformationViewModel @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<InformationCallBack>(appDatabase, interactCommon, scheduler) {

    private var listSkillUserExperience : ArrayList<Skill>? = null
    private var listSkillUserEducation : ArrayList<Skill>? = null
    private var listSkillCertification : ArrayList<Skill>? = null
    private var listSkillLanguage : ArrayList<Skill>? = null
    private var avatarUser : AvatarUser? = null

    // get Experience
    fun getSkillUserExperience(idAccount : String,context: Context){
        listSkillUserExperience = ArrayList()
        val sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM UserSkill WHERE IdAccount = '$idAccount' AND type = '1'")
        while (data.moveToNext()){
            val idSkill = data.getInt(0)
            val name = data.getString(1)
            val type = data.getInt(2)
            val idAccout = data.getString(3)
            listSkillUserExperience!!.add(Skill(idSkill, name, type, idAccout))
        }
    }
    fun getListSkillUserExperience() : ArrayList<Skill>{
        return listSkillUserExperience!!
    }

    // get Education
    fun getSkillUserEducation(idAccount : String,context: Context){
        listSkillUserEducation = ArrayList()
        val sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM UserSkill WHERE IdAccount = '$idAccount' AND type = '2'")
        while (data.moveToNext()){
            val idSkill = data.getInt(0)
            val name = data.getString(1)
            val type = data.getInt(2)
            val idAccout = data.getString(3)
            listSkillUserEducation!!.add(Skill(idSkill, name, type, idAccout))
        }
    }
    fun getListSkillUserEducation() : ArrayList<Skill>{
        return listSkillUserEducation!!
    }

    // get Certification
    fun getSkillUserCertification(idAccount : String,context: Context){
        listSkillCertification = ArrayList()
        val sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM UserSkill WHERE IdAccount = '$idAccount' AND type = '3'")
        while (data.moveToNext()){
            val idSkill = data.getInt(0)
            val name = data.getString(1)
            val type = data.getInt(2)
            val idAccout = data.getString(3)
            listSkillCertification!!.add(Skill(idSkill, name, type, idAccout))
        }
    }
    fun getListSkillUserCertification() : ArrayList<Skill>{
        return listSkillCertification!!
    }

    // get Language
    fun getSkillUserLanguage(idAccount : String,context: Context){
        listSkillLanguage = ArrayList()
        val sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM UserSkill WHERE IdAccount = '$idAccount' AND type = '4'")
        while (data.moveToNext()){
            val idSkill = data.getInt(0)
            val name = data.getString(1)
            val type = data.getInt(2)
            val idAccout = data.getString(3)
            listSkillLanguage!!.add(Skill(idSkill, name, type, idAccout))
        }
    }
    fun getListSkillUserLanguage() : ArrayList<Skill>{
        return listSkillLanguage!!
    }

    fun getAvatarUser(idAccount : String,context: Context){
        val sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM UserAvatar WHERE IdUser = '$idAccount'")
        while (data.moveToNext()){
            val idAvt= data.getInt(0)
            val idAc = data.getString(1)
            val strAvt = data.getString(2)
            avatarUser = AvatarUser(idAvt,idAc,strAvt)
        }
    }
    fun getAvatarUser() : AvatarUser{
        return avatarUser!!
    }
}