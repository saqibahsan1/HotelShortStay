package com.shortstay.pk.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.shortstay.pk.responseModels.SignInResponse


class SharedPreference(var context: Context) {


    fun getBooleanPreference(key: String): Boolean {

        return  preferences.getBoolean(key, false)
    }

    fun setBooleanPreference(key: String, value: Boolean): Boolean {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        return editor.commit()
    }

    fun setIntSharedPreferencesDefaults(key: String, value: Int) {
        val editor = preferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getSignInObject(): SignInResponse? {
        val gson = Gson()
        val userJson = preferences.getString("user", "")
        return gson.fromJson<Any>((userJson), SignInResponse::class.java) as SignInResponse?
    }


    fun setSignInObject(user: SignInResponse?) {
        if (user != null) {
            preferences.edit().putString("user",(user.toJson())).apply()
        } else {
            preferences.edit().putString("user", null).apply()
        }
    }

    var preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    companion object {
        fun getInstance(context: Context) = SharedPreference(context)
    }


}