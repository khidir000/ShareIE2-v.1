package com.example.zhack.share_ie.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.model.User
import com.example.zhack.share_ie.model.status_user_detail
import com.example.zhack.share_ie.model.user_detail
import kotlinx.android.synthetic.main.profile_pribadi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class profile_pribadi : Fragment() {

    private var sessionManagment:SessionManagment?=null
    private var client = ApiClient.create()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_pribadi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManagment = SessionManagment(context)
        sessionManagment!!.checkLogin()

        var ret = client.getUserAll(sessionManagment!!.UserId(),sessionManagment!!.UserToken(),sessionManagment!!.UserId().toInt())
        ret.enqueue(object : Callback<status_user_detail>{
            override fun onFailure(call: Call<status_user_detail>, t: Throwable) {

            }

            override fun onResponse(call: Call<status_user_detail>, response: Response<status_user_detail>) {
                if (response.isSuccessful) {
                    try {
                        var body = response.body()!!.detail
                        nama_pribadi.text = body.get(0).name
                        nim_pribadi.text = body.get(0).username
                        email_pribadi.text = body.get(0).email
                        tgl_pribadi.text = body.get(0).tgl_lahir
                        alamat_pribadi.text = body.get(0).alamat
                        no_pribadi.text = body.get(0).no_hp
                        kbk_pribadi.text = body.get(0).konsentrasi
                        angkatan_pribadi.text = body.get(0).thn_masuk
                    }catch (e:Exception){
                        e.stackTrace
                    }
                }
            }

        })
        logout.setOnClickListener {
            var retro = client.getUserLogout(sessionManagment!!.UserId(),sessionManagment!!.UserToken())
            retro.enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        sessionManagment!!.logoutUser()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                }

            })
        }
    }


}
