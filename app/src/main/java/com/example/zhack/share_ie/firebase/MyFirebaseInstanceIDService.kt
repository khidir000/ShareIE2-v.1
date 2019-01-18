package com.example.zhack.share_ie.firebase

/**
 * Created by ROBOT on 10/12/2017.
 */

import android.util.Log
import com.example.zhack.share_ie.API.ApiClient

import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.model.Token_api
import com.example.zhack.share_ie.model.User
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

import java.io.IOException

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {

    private var sessionManagment:SessionManagment?=null
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(token: String?) {
        sessionManagment = SessionManagment(this)
        SessionManagment.getInstance(applicationContext).saveToken(token)
        val api = ApiClient.create()
            try {
                val ret = api.getUser(sessionManagment!!.UserName(),sessionManagment!!.Password(), Token_api(token))
                ret.enqueue(object : Callback<User>{
                    override fun onFailure(call: Call<User>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<User>, response: Response<User>) {

                    }

                })
            }catch (e:Exception){
                e.stackTrace
            }

    }

    companion object {

        private val TAG = "MyFirebaseIIDService"
    }

}