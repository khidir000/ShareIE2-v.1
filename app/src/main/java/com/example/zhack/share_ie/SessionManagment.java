package com.example.zhack.share_ie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManagment {

    SharedPreferences pref;
    private static SessionManagment mInstance;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    public SessionManagment(Context context){
        this.context = context;
        pref = context.getSharedPreferences(context.getString(R.string.pref_file),PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveToken(String token){
        editor.putString(context.getString(R.string.pref_token),token);
    }
    public void createLoginSession(String id, String token, String pass){
        editor.putBoolean(context.getString(R.string.pref_login_status),true);
        editor.putString(context.getString(R.string.pref_id),id);
        editor.putString(context.getString(R.string.pref_token),token);
        editor.putString("pass",pass);

        editor.commit();
    }

    public void createUserDetail(String username, String name, String foto){
        editor.putString(context.getString(R.string.pref_username),username);
        editor.putString(context.getString(R.string.pref_name),name);
        editor.putString(context.getString(R.string.pref_foto),foto);
        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context,Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    public static synchronized SessionManagment getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SessionManagment(context);
        }
        return mInstance;
    }

    public HashMap<String,String> getUser(){
        HashMap<String,String> user = new HashMap<String, String>();
        user.put(context.getString(R.string.pref_id),pref.getString(context.getString(R.string.pref_id),null));
        user.put(context.getString(R.string.pref_token),pref.getString(context.getString(R.string.pref_token),null));
        user.put(context.getString(R.string.pref_username),pref.getString(context.getString(R.string.pref_username),null));
        user.put(context.getString(R.string.pref_name),pref.getString(context.getString(R.string.pref_name),null));
        user.put(context.getString(R.string.pref_foto),pref.getString(context.getString(R.string.pref_foto),null));
        user.put("pass",pref.getString("pass",null));
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

    public String UserId(){return getUser().get(context.getString(R.string.pref_id));}
    public String UserToken(){return getUser().get(context.getString(R.string.pref_token));}
    public String UserUsername(){return  getUser().get(context.getString(R.string.pref_username));}
    public String UserName(){return  getUser().get(context.getString(R.string.pref_name));}
    public String UserFoto(){return getUser().get(context.getString(R.string.pref_foto));}
    public String Password(){return getUser().get("pass");}
}
