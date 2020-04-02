package com.sph.base

import android.content.Context

object BasicDataPreferenceUtil {
    private lateinit var context: Context
    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun saveJson(key: String, json: String) {
        val sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, json).apply()
    }

    fun getJson(key: String): String {
        val sharedPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
        sharedPreferences.getString(key, "")?.run {
            return this
        }
        return ""
    }


    fun isFirstRun(): Boolean {
        val sharedPreferences = context.getSharedPreferences("firstRun", Context.MODE_PRIVATE)
        return !sharedPreferences.getBoolean("firstRun", false)
    }

    fun saveFirstRun() {
        val sharedPreferences = context.getSharedPreferences("firstRun", Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("firstRun", true).apply()
    }
}