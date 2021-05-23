package com.example.scanningreceiptstest

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.preference.PreferenceManager
import com.example.scanningreceiptstest.database.CURRENT_GROUP
import com.example.scanningreceiptstest.database.CURRENT_USER

object SaveSharedPreference {
    const val PREF_USER_ID = "userID"
    const val PREF_GROUP_ID = "groupID"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun saveUserData(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(PREF_USER_ID, CURRENT_USER!!.phoneNumber)
        editor.putString(PREF_GROUP_ID, CURRENT_GROUP!!.groupID)
        editor.apply()

        Log.i("shared preferences", "user data are saved")
    }

    fun getUserId(context: Context): String? {
        return getSharedPreferences(context).getString(PREF_USER_ID, null)
    }

    fun getGroupId(context: Context): String? {
        return getSharedPreferences(context).getString(PREF_GROUP_ID, null)
    }

    fun clearUserData(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear() //clear all stored data
        editor.apply()
    }
}