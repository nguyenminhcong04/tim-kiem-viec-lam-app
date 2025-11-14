package com.example.apptuyendungvieclam.ui.candidate.answer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apptuyendungvieclam.data.model.job.question.Question
import com.example.apptuyendungvieclam.databinding.ItemAnswerToRequestBinding

class AnswerTheQuestionAdapter(private val inter : IAnswerTheQuestion) :
    RecyclerView.Adapter<AnswerTheQuestionAdapter.Companion.AnswerViewHolder>(){

    companion object {
        class AnswerViewHolder(val binding: ItemAnswerToRequestBinding) : RecyclerView.ViewHolder(binding.root)
    }

    interface IAnswerTheQuestion{
        fun count(): Int
        fun getQuestion(position: Int): Question
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerToRequestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val question = inter.getQuestion(position)
        holder.binding.tvQuestion.text = question.content
    }

    override fun getItemCount(): Int {
        return inter.count()
    }
}