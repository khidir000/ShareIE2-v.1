package com.example.zhack.share_ie.UI

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import android.widget.Toast
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.UI.komentar_profile.komentar_profile
import com.example.zhack.share_ie.berita.AdapterRv
import com.example.zhack.share_ie.komentar.Komentar
import com.example.zhack.share_ie.komentar.adapterKomentar
import com.example.zhack.share_ie.model.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.berita.*
import kotlinx.android.synthetic.main.edit_berita.*
import kotlinx.android.synthetic.main.edit_berita.view.*
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import kotlinx.android.synthetic.main.profil_berita_isi.view.*
import kotlinx.android.synthetic.main.profile_berita.*
import kotlinx.android.synthetic.main.tampilan_berita.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition
import kotlin.coroutines.experimental.coroutineContext

class profile_berita: Fragment(),adapter_profile.Listener{

    private var mCompositeDisposable: CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataBerita>?=null
    public var mListKomentar:ArrayList<DataKomentar>?=null
    private val retrofit = ApiClient.create()
    private var hashMap = hashMapOf<Int, String>()
    private var madapter: adapter_profile? = null
    public var adapter: adapterKomentar? = null
    private var posisi = null
    private var berita:String?=null
    var sessionManagment: SessionManagment? = null

    override fun onItemClick(view: View, position: Int) {
       showOptionsMenu(view, position)
    }

    override fun onKomentar(view: View, position: Int) {
        val komentar = komentar_profile(position)
        komentar.show(fragmentManager?.beginTransaction(), komentar.tag)
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
    public fun showOptionsMenu(view: View, position: Int) {
        val popup = PopupMenu(view.context, view)
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.menu_berita, popup.menu)
        popup.setOnMenuItemClickListener(MenuItemClickListener(position))
        popup.show()
    }

    private inner class MenuItemClickListener(position: Int) : PopupMenu.OnMenuItemClickListener {
        var posisi  = position
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.hapus ->{
                    Log.d("id_hapus", posisi.toString())
                    var berita_hapus = retrofit.getDeleteBerita(sessionManagment!!.UserId(), sessionManagment!!.UserToken(),hashMap[posisi]!!.toInt())
                    berita_hapus.enqueue(object : Callback<status>{
                        override fun onFailure(call: Call<status>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<status>, response: Response<status>) {
                            if (response.isSuccessful) {
                                initRecyclerView()
                                showList()
                                callWebService()
                                Toast.makeText(context, "Data Telah dihapus", Toast.LENGTH_SHORT).show()
                            }
                        }

                    })
                }
                R.id.edit->{
                    val dialog  = AlertDialog.Builder(context)
                    var dialogview = layoutInflater.inflate(R.layout.edit_berita, null)
                    dialog.setView(dialogview)
                    dialog.setCancelable(true)
                    dialog.setTitle("Edit Berita")
                    dialog.setPositiveButton("Ganti"){
                        dialog, which->
                        berita = dialogview.edit_berita.text.toString()
                        Log.d("profile_berita2", berita.toString())
                        if (berita!=null){
                            var edit_berita = retrofit.getUpdateBerita(sessionManagment!!.UserId(), sessionManagment!!.UserToken(), hashMap[posisi]!!.toInt(), berita_edit(berita))
                            edit_berita.enqueue(object : Callback<status> {
                                override fun onFailure(call: Call<status>, t: Throwable) {
                                    Toast.makeText(context, "Data Telah dihapus gagal", Toast.LENGTH_SHORT).show()
                                }

                                override fun onResponse(call: Call<status>, response: Response<status>) {
                                    if (response.isSuccessful) {
                                        initRecyclerView()
                                        showList()
                                        callWebService()
                                        Toast.makeText(context, "Data Telah diganti", Toast.LENGTH_SHORT).show()
                                    }
                                }

                            })
                        }
                    }
                    dialog.show()

                }
            }
            return false
        }

    }
    private fun callWebService(){


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
                                        for (i in 0..mAndroidList!!.count()-1){
                                            hashMap[i] = datum.get(i).id_berita!!.toString()
                                        }
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