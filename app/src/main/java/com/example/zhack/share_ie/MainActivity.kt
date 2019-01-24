package com.example.zhack.share_ie

import android.content.DialogInterface
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.UI.profile_berita
import com.example.zhack.share_ie.UI.seminar_list
import com.example.zhack.share_ie.berita.control_berita
import com.example.zhack.share_ie.model.status
import com.example.zhack.share_ie.model.status_user_detail
import com.example.zhack.share_ie.model.user_detail
import com.example.zhack.share_ie.profile.profile_Ui
import com.squareup.picasso.Picasso
import com.yarolegovich.lovelydialog.LovelyInfoDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.berita.*
import org.jetbrains.anko.appcompat.v7.alertDialogLayout
import org.jetbrains.anko.image
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var session:SessionManagment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        session = SessionManagment(applicationContext)
        session!!.checkLogin()

        var foto = session!!.UserFoto()
        Picasso.get().load(foto).into(user_menu)

        var apiclient = ApiClient.create()

        var retrofit = apiclient.getDetail(session!!.UserId(),session!!.UserToken())
        retrofit.enqueue(object : Callback<status> {
            override fun onFailure(call: Call<status>, t: Throwable) {

            }

            override fun onResponse(call: Call<status>, response: Response<status>) {
                if (response.isSuccessful) {
                    var response = response.body()!!
                    if (response!!.status!!.equals(200)){
                            initFragment(control_berita())
                            berita.setColorFilter(R.color.colorPrimaryDark)
                            berita.isEnabled = false
                            berita.setOnClickListener {
                                initFragment(control_berita())
                                berita.setColorFilter(R.color.colorPrimaryDark)
                                berita.isEnabled = false
                                user_menu.isEnabled = true
                                jadwal.isEnabled = true
                                seminar.isEnabled = true
                                seminar.clearColorFilter()
                                user_menu.alpha = 0.75F
                                jadwal.clearColorFilter()
                                laporan.clearColorFilter()
                            }
                            seminar.setOnClickListener {
                                initFragment(seminar_list())
                                seminar.setColorFilter(R.color.colorPrimaryDark)
                                berita.clearColorFilter()
                                berita.isEnabled = true
                                user_menu.isEnabled = true
                                jadwal.isEnabled = true
                                seminar.isEnabled = false
                                user_menu.alpha = 0.75F
                                jadwal.clearColorFilter()
                                laporan.clearColorFilter()
                            }

                            user_menu.setOnClickListener{
                                initFragment(profile_Ui())
                                berita.clearColorFilter()
                                berita.isEnabled = true
                                seminar.isEnabled = true
                                jadwal.isEnabled = true
                                user_menu.isEnabled = false
                                jadwal.clearColorFilter()
                                seminar.clearColorFilter()
                                laporan.clearColorFilter()
                                user_menu.alpha = 1F
                            }
                        jadwal.setOnClickListener {
                            initFragment(com.example.zhack.share_ie.jadwal.jadwal())
                            berita.clearColorFilter()
                            berita.isEnabled = true
                            seminar.isEnabled = true
                            user_menu.isEnabled = true
                            jadwal.isEnabled = false
                            jadwal.setColorFilter(R.color.colorPrimaryDark)
                            seminar.clearColorFilter()
                            laporan.clearColorFilter()
                            user_menu.alpha = 0.75F
                        }
                    }
                }else{
                    session!!.logoutUser()
                }
            }

        })


    }

    private fun initFragment(classFragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, classFragment)
        transaction.commit()
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
