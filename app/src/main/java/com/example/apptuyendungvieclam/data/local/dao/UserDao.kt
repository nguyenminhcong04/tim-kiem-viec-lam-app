package com.example.apptuyendungvieclam.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.apptuyendungvieclam.data.model.UserExample

@Dao
interface UserDao {
    // Thao tác READ
    @Query(value = "SELECT * FROM User WHERE User.id=:userId")
    fun findUserById(userId: Int) : UserExample

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(item: UserExample): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListUser(item: MutableList<UserExample>)

    @Query(value = "SELECT * FROM User")
    fun findAll(): MutableList<UserExample>
}