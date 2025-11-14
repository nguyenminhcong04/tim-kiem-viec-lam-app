package com.example.apptuyendungvieclam.data.remote

import com.example.apptuyendungvieclam.data.model.UserExample
import com.example.apptuyendungvieclam.data.model.api.UserResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiUser {
    @GET("/api/users")
    fun getUsers(
    ): Observable<UserResponse<MutableList<UserExample>>>
}