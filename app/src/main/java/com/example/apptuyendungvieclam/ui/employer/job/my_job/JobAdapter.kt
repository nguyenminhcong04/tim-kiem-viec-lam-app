package com.example.apptuyendungvieclam.ui.employer.job.my_job

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.User
import com.example.apptuyendungvieclam.data.model.job.Job
import com.example.apptuyendungvieclam.databinding.ItemJobBinding

class JobAdapter(private val inter : IJob, val context: Context,val user : User): RecyclerView.Adapter<JobAdapter.Companion.JobViewHolder>() {
    companion object {
        class JobViewHolder(val binding: ItemJobBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface IJob {
        fun count(): Int
        fun getJob(position: Int): Job
        fun onClickJob(position: Int)
        fun onClickPower(position: Int)
        fun onClickDelete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = ItemJobBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = inter.getJob(position)
        if(user.permission == 0){
            holder.binding.imgPower.visibility = View.VISIBLE
            holder.binding.btnDeleteJob.visibility = View.VISIBLE
        }else if(user.permission == 1){
            holder.binding.imgPower.visibility = View.GONE
            holder.binding.btnDeleteJob.visibility = View.GONE
        }
        holder.binding.jobName.text = job.jobName
        holder.binding.companyName.text = job.company!!.companyName
        Glide.with(context).load(job.company!!.companyAvatar)
            .placeholder(R.drawable.img_company_load)
            .error(R.drawable.img_company_false)
            .into(holder.binding.avtCompany)
        if(job.status == 1){
            holder.binding.imgPower.setImageResource(R.drawable.ic_power_on)
        }else if(job.status == 2){
            holder.binding.imgPower.setImageResource(R.drawable.ic_power_off)
        }
        holder.itemView.setOnClickListener { inter.onClickJob(position) }
        holder.binding.imgPower.setOnClickListener { inter.onClickPower(position) }
        holder.binding.btnDeleteJob.setOnClickListener { inter.onClickDelete(position) }
    }

    override fun getItemCount(): Int {
        return inter.count()
    }

}

