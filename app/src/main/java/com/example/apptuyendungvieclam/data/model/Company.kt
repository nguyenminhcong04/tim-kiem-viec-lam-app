package com.example.apptuyendungvieclam.data.model

import java.io.Serializable

data class Company(
    var idCompany : Int = 0,
    var companyName: String = "",
    var companyAvatar: String = "",
    var companyAdress: String = ""
) : Serializable

//@Entity(tableName = "company")
//class Company {
//    @PrimaryKey
//    @ColumnInfo(name = "id")
//    var id : Int = 0
//
//    @ColumnInfo(name = "company_name")
//    @SerializedName("company_name")
//    var companyName: String = ""
//
//    @ColumnInfo(name = "company_avatar")
//    @SerializedName("company_avatar")
//    var companyAvatar: String = ""
//}