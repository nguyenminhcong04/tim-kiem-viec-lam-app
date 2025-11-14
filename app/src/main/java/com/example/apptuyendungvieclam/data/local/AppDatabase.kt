package com.example.apptuyendungvieclam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.apptuyendungvieclam.data.local.dao.UserDao
import com.example.apptuyendungvieclam.data.model.UserExample

@Database(entities = arrayOf(UserExample::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
//    abstract fun fileDao() : FileDao
    abstract fun userDao() : UserDao
}