package com.example.apptuyendungvieclam.data.model.job

import com.example.apptuyendungvieclam.data.model.Company
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.question.Question
import com.example.apptuyendungvieclam.data.model.job.skill.Skill
import java.io.Serializable

data class Job(var idJob : Int =0,
               var codeJob : String = "",
               var jobName: String = "",
               var description: String = "",
               var listSkill : ArrayList<Skill>? = null,
               var listQuestion: ArrayList<Question>? = null,
               var right: String = "",
               var amount: Int = 0,
               var status: Int = 0,
               var employer : User? = null,
               var company: Company? = null
) : Serializable

//@Entity(tableName = "job")
//class Job {
//    @PrimaryKey
//    @ColumnInfo(name = "id")
//    var id : Int = 0
//
//    @ColumnInfo(name = "job_name")
//    @SerializedName("job_name")
//    var jobName: String = ""
//
//// //   @ColumnInfo(name = "employer")
////    @SerializedName("employer")
////    lateinit var employer: Employer
//
//    @ColumnInfo(name = "description")
//    @SerializedName("description")
//    var description: String = ""
//
//    @ColumnInfo(name = "request")
//    @SerializedName("request")
//    var request: String = ""
//
//    @ColumnInfo(name = "right")
//    @SerializedName("right")
//    var right: String = ""
//
//
//    @ColumnInfo(name = "amount")
//    @SerializedName("amount")
//    var amount: Int = 0
//}