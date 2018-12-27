package com.example.zhack.share_ie.UI

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.MainActivity
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.model.status
import com.example.zhack.share_ie.model.status_created
import com.squareup.picasso.Picasso
import com.yarolegovich.lovelydialog.LovelyProgressDialog
import kotlinx.android.synthetic.main.buat_status.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class create_status:AppCompatActivity(){
    var loading:LovelyProgressDialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buat_status)
        var session = SessionManagment(applicationContext)

        session!!.checkLogin()

        loading = LovelyProgressDialog(this).setCancelable(false)

        var username = session!!.UserUsername()
        var name = session!!.UserName()
        var foto = session!!.UserFoto()

        nama_user_status.setText(name)
        nim_user_status.setText(username)
        Picasso.get().load(foto).into(foto_user_status)


        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        
        bagikan.setOnClickListener {

                var tulis_status = tulis_status.text.toString()
                var api = ApiClient.create()
                var retro = api.create_status(session!!.UserId(),session!!.UserToken(), status_created(session!!.UserId(),tulis_status))

            if(tulis_status.length>0) {
                loading!!.setIcon(R.mipmap.ic_launcher)
                        .setTitle("Bagikan..")
                        .setTopColorRes(R.color.colorPrimary)
                        .show()
                retro.enqueue(object : Callback<status> {
                    override fun onFailure(call: Call<status>, t: Throwable) {
                        Log.d("bagikan", "error parah")
                    }

                    override fun onResponse(call: Call<status>, response: Response<status>) {
                        if (response.isSuccessful) {
                            loading!!.dismiss()
                            val response = response.body()!!.status
                            if (response!!.equals(201)) {
                                val intent = Intent(this@create_status, MainActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            Log.d("bagikan", "errror biasa")
                        }

                    }

                })
                }else{
                Toast.makeText(this,"Berita tidak boleh kosong",Toast.LENGTH_SHORT).show()
            }

            }
        }


    }