package com.example.apptuyendungvieclam.data.sqlite

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class SQLiteHelper(context: Context?, name: String?, factory: CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    // Thao tác CREATE, UPDATE, DELETE (Executes SQL statement)
    fun QueryData(sql: String?) {
        val database = writableDatabase
        database.execSQL(sql)
    }

    fun GetData(sql: String?): Cursor {
        val database = readableDatabase
        return database.rawQuery(sql, null)
    }

    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}