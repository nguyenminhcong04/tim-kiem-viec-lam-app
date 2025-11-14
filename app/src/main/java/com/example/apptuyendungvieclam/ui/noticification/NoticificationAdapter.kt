package com.example.apptuyendungvieclam.ui.noticification

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apptuyendungvieclam.R
import com.example.apptuyendungvieclam.data.model.NotificationItem
import com.example.apptuyendungvieclam.databinding.ItemNotificationBinding

class NoticificationAdapter(private val inter : INotification) : RecyclerView.Adapter<NoticificationAdapter.Companion.NotificationViewHolder>(){

    companion object{
        class NotificationViewHolder(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root)
    }
    interface INotification{
        fun count(): Int
        fun getNotification(position: Int): NotificationItem
        // Đây là hàm Callback/Interface được Fragment/Activity chứa Adapter thực thi
        fun onClickViewProfile(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notificationItem = inter.getNotification(position)
        holder.binding.tvContentNotification.text = notificationItem.candidate!!.name+" đã ứng tuyển vào công việc "+notificationItem.job!!.jobName
        holder.itemView.setOnClickListener { v-> inter.onClickViewProfile(position) }

        val bytes: ByteArray = Base64.decode(notificationItem.avatarUser!!.avt, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        Glide.with(holder.binding.avtUser)
            .load(bitmap)
            .error(R.drawable.avatardefult1)
            .into(holder.binding.avtUser)
    }

    override fun getItemCount(): Int {
        return inter.count()
    }
}