package com.example.zhack.share_ie

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ContentFrameLayout
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.firebase.MyFirebaseInstanceIDService
import com.example.zhack.share_ie.model.Token_api
import com.example.zhack.share_ie.model.User
import com.example.zhack.share_ie.model.user_detail
import com.google.android.gms.common.api.Api
import com.google.firebase.iid.FirebaseInstanceId
import com.yarolegovich.lovelydialog.LovelyChoiceDialog
import com.yarolegovich.lovelydialog.LovelyProgressDialog
import kotlinx.android.synthetic.main.login_username.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.jetbrains.anko.db.INTEGER
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {
    var username: String? = null
    var password: String? = null
    var APIToken:String? = null
    var session:SessionManagment?=null
    var loadingdialog:LovelyProgressDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_username)
        session = SessionManagment(this)
        loadingdialog = LovelyProgressDialog(this).setCancelable(false)
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

        username = et_username.text.toString()
        password = et_password.text.toString()
        APIToken = FirebaseInstanceId.getInstance().token

        loadingdialog!!.setIcon(R.mipmap.ic_launcher)
                .setTitle("Login..")
                .setTopColorRes(R.color.colorPrimary)
                .show()

        val api = ApiClient.create()
        Log.d("pesan",APIToken.toString())
        val getApi = api.getUser(username!!, password!!, Token_api(APIToken))
        getApi.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(this@Login,t.message,Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    loadingdialog!!.dismiss()
                    if(response.body()!!.status.equals(200))
                    {
                        var token = response.body()!!.token
                        var id = response.body()!!.id

                        Log.d("token", token)

                        session!!.createLoginSession(id,token,password)

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

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Keluar")
        dialog.setMessage("Yakin Ingin Keluar ?")
        dialog.setPositiveButton("YA"){dialog, which->
            val intent = Intent(applicationContext,Login::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("EXIT",true)
            startActivity(intent)
            finishAffinity()
        }
        dialog.setNegativeButton("TIDAK"){dialog,which->
            dialog.cancel()
        }
        dialog.show()
    }

}