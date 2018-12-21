package com.example.zhack.share_ie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    public SessionManagment(Context context){
        this.context = context;
        pref = context.getSharedPreferences(context.getString(R.string.pref_file),PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String id, String token){
        editor.putBoolean(context.getString(R.string.pref_login_status),true);
        editor.putString(context.getString(R.string.pref_id),id);
        editor.putString(context.getString(R.string.pref_token),token);

        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context,Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public void wasLogin(){
        if(this.isLoggedIn()){
            Intent i = new Intent(context,MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public HashMap<String,String> getUser(){
        HashMap<String,String> user = new HashMap<String, String>();
        user.put(context.getString(R.string.pref_id),pref.getString(context.getString(R.string.pref_id),null));
        user.put(context.getString(R.string.pref_token),pref.getString(context.getString(R.string.pref_token),null));
        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, Login.class);

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(context.getString(R.string.pref_login_status),false);
    }
}
