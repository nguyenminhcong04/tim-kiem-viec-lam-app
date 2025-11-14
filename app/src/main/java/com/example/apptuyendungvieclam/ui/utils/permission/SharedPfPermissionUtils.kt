package com.example.apptuyendungvieclam.ui.utils.permission

import android.content.Context
object SharedPfPermissionUtils {
    // 1. Tên file Shared Preferences
    val SYSTEM_CONFIG = "SYSTEM_CONFIG"
    // 2. Đọc trạng thái (READ)
    @JvmStatic
    fun getNumberDeniedPermission(context: Context, permissionType: String): Int {
        val sf = context.getSharedPreferences(SYSTEM_CONFIG, Context.MODE_PRIVATE)
        return sf.getInt(permissionType, 0)
    }
    // 3. Tăng/Cập nhật số lần từ chối (WRITE/UPDATE)
    @JvmStatic
    fun increaseNumberDeniedPermission(context: Context, permissionType: String, numberIncrease: Int) {
        val sf = context.getSharedPreferences(SYSTEM_CONFIG, Context.MODE_PRIVATE)
        val numberCurrent = sf.getInt(permissionType, 0)
        val editor = sf.edit()
        editor.putInt(permissionType, numberIncrease + numberCurrent)
        editor.apply()
    }
    // 4. Ghi đè trạng thái (WRITE)
    @JvmStatic
    fun saveNumberDeniedPermission(context: Context, permissionType: String, number: Int) {
        val sf = context.getSharedPreferences(SYSTEM_CONFIG, Context.MODE_PRIVATE)
        val editor = sf.edit()
        editor.putInt(permissionType, number)
        editor.apply()
    }
}