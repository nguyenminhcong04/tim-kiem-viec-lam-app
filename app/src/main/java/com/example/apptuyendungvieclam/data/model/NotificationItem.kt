package com.example.apptuyendungvieclam.data.model

import com.example.apptuyendungvieclam.data.model.job.Job
import java.io.Serializable

data class NotificationItem(var id : Int = 0 ,
                             var candidate : User? = null,
                             var job : Job? = null,
                             var avatarUser: AvatarUser? = null) : Serializable