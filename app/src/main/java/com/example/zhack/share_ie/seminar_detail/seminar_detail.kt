package com.example.zhack.share_ie.seminar_detail

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.app.AlertDialog
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
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class seminar_detail:AppCompatActivity(),Adapter_seminar.Listener{


    var session:SessionManagment?=null
    var KEY_INTENT:String = "key"
    private var position:Int = 0
    private var hashMap = HashMap<Int, String>()
    private var mAndroidList:ArrayList<seminar>?=null
    var adapter:Adapter_seminar?=null

    @SuppressLint("RestrictedApi")
    override fun onItemClick(view: View, position:Int) {
        this.position = position
        var inten = Intent(this,dosen_seminar::class.java)
        inten.putExtra("nilai", hashMap[position]!!.toInt())
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                var option = ActivityOptions.makeSceneTransitionAnimation(this,fab_dosen_seminar,fab_dosen_seminar.transitionName)
                startActivityForResult(inten,2000)
            }else{
                startActivityForResult(inten,2000)
            }


//        var dialog = AlertDialog.Builder(this)
//        var view = layoutInflater.inflate(R.layout.activity_dosen_seminar, null)
//        dialog.setView(view)

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

                    for (i in 0..list.count()-1){
                        hashMap[i] = list.get(i).id_seminar.toString()
                    }
                    mAndroidList = ArrayList(list)
                    adapter = Adapter_seminar(mAndroidList!!,this@seminar_detail)
                    recycler.adapter = adapter

                }
            }

        })
    }

    public fun hasmap():HashMap<Int, String>{
        var hashMap:HashMap<Int,String> = hashMap
        return hashMap
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