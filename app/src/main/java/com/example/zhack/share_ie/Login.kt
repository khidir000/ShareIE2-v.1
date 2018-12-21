package com.example.zhack.share_ie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ContentFrameLayout
import android.view.View
import android.widget.Toast
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.model.User
import kotlinx.android.synthetic.main.login_username.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    var username: String? = null
    var password: String? = null
    var session:SessionManagment?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_username)
        session = SessionManagment(this)
        if(intent.getBooleanExtra("EXIT",false)){
            finish()
            return
        }else{
            session!!.wasLogin()
        }
        login.setOnClickListener {
            get_login()
        }
    }
    fun get_login(){
        val httpClient = OkHttpClient().newBuilder()
        val interceptor = Interceptor { chain ->
            val request = chain?.request()?.newBuilder()?.header("Client-Service","frontend-client")?.header("Auth-Key","simplerestapi")?.build()
            chain?.proceed(request)
        }
        httpClient.networkInterceptors().add(interceptor)

        username = et_username.text.toString()
        password = et_password.text.toString()
        val api = ApiClient.create()
        val getApi = api.getUser(username!!, password!!)
        getApi.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@Login,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    if(response.body()!!.status.equals(200))
                    {
                        val token = response.body()!!.token
                        val id = response.body()!!.id

                        session!!.createLoginSession(id,token)

                        val intent = Intent(this@Login,MainActivity::class.java)
                        startActivity(intent)
                    }else if (response.body()!!.message.equals("Wrong password.")){
                        Toast.makeText(this@Login,"Password Salah",Toast.LENGTH_LONG).show()
                    }else if (response.body()!!.message.equals("Username not found.")){
                        Toast.makeText(this@Login,"User Tidak Ditemukan",Toast.LENGTH_LONG).show()
                    }else if (response.body()!!.status.equals(401)){
                        //setContentView(R.Layout.*)
                        Toast.makeText(this@Login,"Anda Sudah Login di Device Yang Lain",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@Login,"Terjadi Kesalahan Pada Server",Toast.LENGTH_LONG).show()
                    }
                }
            }

        })
    }

}