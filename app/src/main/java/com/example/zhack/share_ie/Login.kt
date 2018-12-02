package com.example.zhack.share_ie

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.zhack.share_ie.API.*
import com.example.zhack.share_ie.API.Login
import kotlinx.android.synthetic.main.login_username.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_username)




        login.setOnClickListener {
            val inte = Intent(this@Login
                    ,MainActivity::class.java)
            startActivity(inte)
//            val httpClient = OkHttpClient().newBuilder()
//            val interceptor = Interceptor { chain ->
//                val request = chain?.request()?.newBuilder()?.header("Client-Service","frontend-client")?.header("Auth-Key","simplerestapi")?.build()
//                chain?.proceed(request)
//            }
//
//            httpClient.networkInterceptors().add(interceptor)
//            val retrofit = ApiClient.create()
//            val panggil = retrofit.getDetail("1","$1$.WTaRTC4"+"$"+"h1gvoffq..oHft1eslTeS1")
//            panggil.enqueue(object :Callback<List<DataBerita>>{
//                override fun onFailure(call: Call<List<DataBerita>>, t: Throwable) {
////                    Toast.makeText(this@Login,t.toString(),Toast.LENGTH_LONG).show()
//                    Log.e("pesan",t.toString())
//                }
//
//                override fun onResponse(call: Call<List<DataBerita>>, response: Response<List<DataBerita>>) {
//                    if (response.isSuccessful) {
//                        Toast.makeText(this@Login,"sukses",Toast.LENGTH_LONG).show()
//                    }else{
//                        Toast.makeText(this@Login,"gagal",Toast.LENGTH_LONG).show()
//                    }
//                   // copyright.setText(nil)
//                }
//            })
        }


    }

    private fun performLogin(){
        val uname = et_username.text.toString()
        val pass = et_password.text.toString()
    }
}
