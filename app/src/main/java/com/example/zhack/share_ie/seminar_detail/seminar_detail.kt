package com.example.zhack.share_ie.seminar_detail

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.StaticLayout
import android.view.View
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.UI.dosen_seminar
import com.example.zhack.share_ie.model.seminar
import com.example.zhack.share_ie.model.status_seminar
import kotlinx.android.synthetic.main.activity_dosen_seminar.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.buat_status.*
import kotlinx.android.synthetic.main.list_seminar_category.*
import kotlinx.android.synthetic.main.seminar.*
import kotlinx.android.synthetic.main.seminar.view.*
import org.jetbrains.anko.startActivityForResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class seminar_detail:AppCompatActivity(),Adapter_seminar.Listener{


    var session:SessionManagment?=null
    var KEY_INTENT:String = "key"
    private var mAndroidList:ArrayList<seminar>?=null
    var adapter:Adapter_seminar?=null

    @SuppressLint("RestrictedApi")
    override fun onItemClick(view: View) {
        view.fab_dosen_seminar.setOnClickListener {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                var option = ActivityOptions.makeSceneTransitionAnimation(this,fab_dosen_seminar,fab_dosen_seminar.transitionName)
                startActivityForResult(Intent(this,dosen_seminar::class.java),2000,option.toBundle())
            }else{
                startActivityForResult(Intent(this,dosen_seminar::class.java),2000)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.list_seminar_category)

        session = SessionManagment(applicationContext)

        session!!.checkLogin()

        setSupportActionBar(toolbar_all)
        supportActionBar!!.title = "Seminar " + intent.getStringExtra("key")

        detail()
        initRecyclerView()
    }

    fun detail(){

        var jenis = intent.getStringExtra("key")
        var api = ApiClient.create()
        var retrofit = api.getSeminar(session!!.UserId(),session!!.UserToken(),jenis)

        retrofit.enqueue(object : Callback<status_seminar>{
            override fun onFailure(call: Call<status_seminar>, t: Throwable) {

            }

            override fun onResponse(call: Call<status_seminar>, response: Response<status_seminar>) {
                if (response.isSuccessful) {

                    var list:List<seminar> = response.body()!!.detail
                    mAndroidList = ArrayList(list)
                    adapter = Adapter_seminar(mAndroidList!!,this@seminar_detail)
                    recycler.adapter = adapter

                }
            }

        })
    }

    private fun initRecyclerView() {

        recycler.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        recycler.layoutManager = layoutManager
    }

//    @SuppressLint("RestrictedApi")
//    public fun dosen_list(view: View){
//        window.exitTransition = null
//        window.enterTransition = null
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
//            var option = ActivityOptions.makeSceneTransitionAnimation(this,fab_dosen_seminar,fab_dosen_seminar.transitionName)
//            startActivityForResult(Intent(this,dosen_seminar::class.java),2000,option.toBundle())
//        }else{
//            startActivityForResult(Intent(this,dosen_seminar::class.java),2000)
//        }
//    }
}