package com.example.zhack.share_ie

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

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

    public fun writeToken(token:String){
        var editor = sharedPreferences.edit()
        editor.putString(context.getString(R.string.pref_token),token)
        editor.commit()
    }

    public fun writeId(id:String){
        var editor = sharedPreferences.edit()
        editor.putString(context.getString(R.string.pref_id),id)
    }

    public fun readToken():String{
        return sharedPreferences.getString(context.getString(R.string.pref_token),"Token")
    }

    public fun readId():String{
        return sharedPreferences.getString(context.getString(R.string.pref_id),"Id")
    }

    public fun tampilToas(pesan:String){
        Toast.makeText(context,pesan,Toast.LENGTH_LONG).show()
    }

}