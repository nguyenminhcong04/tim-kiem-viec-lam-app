package com.example.apptuyendungvieclam.ui.employer.create_job.create_question

import android.content.Context
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.model.job.question.Question
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.data.sqlite.SQLiteHelper
import com.example.apptuyendungvieclam.ui.base.viewmodel.BaseViewModel
import java.util.concurrent.Executor
import javax.inject.Inject

class CreateQuestionViewModel  @Inject constructor(
    appDatabase: AppDatabase,
    interactCommon: InteractCommon,
    scheduler: Executor
) : BaseViewModel<CreateQuestionCallBack>(appDatabase, interactCommon, scheduler) {

    lateinit var idJob : String
    private var edQuestion : String? = null
    private lateinit var sqLiteHelper : SQLiteHelper
    private var questionList : ArrayList<Question>? = null

    companion object{
        const val ADD_QUESTION = 1
        const val ADD_QUESTION_SUCCESS = 2
        const val DELETE_QUESTION_SUCCESS = 3
        const val ON_CLICK_NEXT_TO_RIGHT = 4
        const val ON_CLICK_UPDATE_QUESTION = 5
    }
    fun onClickUpdateQuestion(){
        uiEventLiveData.value = ON_CLICK_UPDATE_QUESTION
    }
    fun onClickNext(){
        uiEventLiveData.value = ON_CLICK_NEXT_TO_RIGHT
    }
    fun onClickAddQuestion(){
        uiEventLiveData.value = ADD_QUESTION
    }
    fun addQuestion(edQuestion: String?,context: Context?){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("INSERT INTO Question VALUES(null,'$edQuestion','$idJob')")
        uiEventLiveData.value = ADD_QUESTION_SUCCESS
    }
    fun getQuestion(context: Context?){
        questionList = ArrayList()
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        val data = sqLiteHelper.GetData("SELECT * FROM Question WHERE IdJob = '$idJob'")
        while (data.moveToNext()) {
            val id = data.getInt(0)
            val content = data.getString(1)
            val idJob = data.getString(2)
            val question = Question(id,content,idJob)
            questionList!!.add(question)
        }
    }
    fun getListQuestion() : ArrayList<Question>{
        return questionList!!
    }
    fun deleteQuestion(question: Question,context: Context?){
        sqLiteHelper = SQLiteHelper(context, "Data.sqlite", null, 5)
        sqLiteHelper.QueryData("DELETE FROM Question WHERE IdJob = '$idJob' AND Id ='${question.id}'")
        uiEventLiveData.value = DELETE_QUESTION_SUCCESS
    }
}