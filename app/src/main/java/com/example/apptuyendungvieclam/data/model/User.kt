package com.example.apptuyendungvieclam.data.model

import java.io.Serializable

data class User (var idAccount: String = "",
                 var email: String = "",
                 var password: String = "",
                 var name: String = "",
                 var age: Int = 0,
                 var phone: String ="",
                 var permission: Int = 0) : Serializable

//@Entity(tableName = "user")
//open class User : Serializable{
//    @PrimaryKey
//    @ColumnInfo(name = "id")
//    var idAccount: String = ""
//
//    @ColumnInfo(name = "username")
//    @SerializedName("username")
//    var username: String = ""
//
//    @ColumnInfo(name = "password")
//    @SerializedName("password")
//    var password: String = ""
//
//    @ColumnInfo(name = "name")
//    @SerializedName("name")
//    var name: String = ""
//
//    @ColumnInfo(name = "age")
//    @SerializedName("age")
//    var age: Int = 0
//
//    @ColumnInfo(name = "phone")
//    @SerializedName("phone")
//    var phone: String = ""
//
//    @ColumnInfo(name = "permission")
//    @SerializedName("permission")
//    var permission: Int = 0
//}

