package com.example.zhack.share_ie.UI

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.berita.AdapterRv
import com.example.zhack.share_ie.komentar.adapterKomentar
import com.example.zhack.share_ie.model.DataBerita
import com.example.zhack.share_ie.model.DataKomentar
import com.example.zhack.share_ie.model.status
import com.example.zhack.share_ie.model.status_user_detail
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.berita.*
import kotlinx.android.synthetic.main.profil_berita_isi.view.*
import kotlinx.android.synthetic.main.profile_berita.*
import kotlinx.android.synthetic.main.tampilan_berita.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.experimental.coroutineContext

class profile_berita: Fragment(),adapter_profile.Listener{

    private var mCompositeDisposable: CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataBerita>?=null
    public var mListKomentar:ArrayList<DataKomentar>?=null
    private var madapter: adapter_profile? = null
    public var adapter: adapterKomentar? = null
    var sessionManagment: SessionManagment? = null

    override fun onItemClick(view: View) {
        view.dots.setOnClickListener {
            showOptionsMenu(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.profile_berita,container,false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sessionManagment = SessionManagment(context)
        sessionManagment!!.checkLogin()
        super.onViewCreated(view, savedInstanceState)
        mCompositeDisposable = CompositeDisposable()

        try {
            callWebService()
            initRecyclerView()
            showList()
        }
        catch (e:Exception){
            e.message
        }
    }
    public fun showOptionsMenu(view: View) {
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_berita, popup.menu)
        popup.setOnMenuItemClickListener(MenuItemClickListener())
        popup.show()
    }

    private inner class MenuItemClickListener : PopupMenu.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.hapus ->{}
            }
            return false
        }

    }
    private fun callWebService(){

        val retrofit = ApiClient.create()
        val panggil = retrofit.getBeritaDetail(sessionManagment!!.UserId(),sessionManagment!!.UserToken(),Integer.parseInt(sessionManagment!!.UserId()))
        Log.d("session = ", sessionManagment!!.UserId().toString())
        panggil.enqueue(object : Callback<status> {
            override fun onFailure(call: Call<status>, t: Throwable) {
//                    Toast.makeText(this@Login,t.toString(),Toast.LENGTH_LONG).show()
                Log.e("pesan",t.toString())
            }
            override fun onResponse(call: Call<status>, response: Response<status>) {
                if (response.isSuccessful) {
                    val datum = response.body()!!.detail
                    if (response.body()!!.status!!.equals(200)) {
                                var list:List<DataBerita> = response.body()!!.detail
                                mAndroidList = ArrayList(list)
                                madapter = adapter_profile(mAndroidList!!, this@profile_berita)
                                if(mAndroidList!!.count() >0){
                                    try{
                                        rv_profile_berita.adapter = madapter
                                        rv_profile_berita.visibility = View.VISIBLE
                                    }catch (e:Exception){
                                        e.message
                                    }
                                }else{
                                    try {

                                    }catch (e:Exception){
                                        e.message
                                    }
                                }


                    }else{
                        sessionManagment!!.logoutUser()
                        Log.e("pesan",response.message())
                    }
                }else{
                    sessionManagment!!.logoutUser()
                }

                // copyright.setText(nil)
            }
        })
    }

    private fun initRecyclerView() {

        rv_profile_berita.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv_profile_berita.layoutManager = layoutManager
    }

    fun showList(){
        mAndroidList?.clear()
        mAndroidList?.addAll(mAndroidList!!)
        madapter?.notifyDataSetChanged()
    }
}