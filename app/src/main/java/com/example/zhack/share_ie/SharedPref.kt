package com.example.zhack.share_ie

import android.content.Context
import android.content.SharedPreferences

public class SharedPref
{
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var context: Context


    fun SharedPref(context: Context){
        this.context = context
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file),Context.MODE_PRIVATE)
    }

    public fun tulisLoginStatus(status:Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(context.getString(R.string.pref_login_status),status)
        editor.commit()
    }

    public fun bacaLoginStatus():Boolean{
        return sharedPreferences.getBoolean(context.getString(R.string.pref_login_status),false)
    }

}