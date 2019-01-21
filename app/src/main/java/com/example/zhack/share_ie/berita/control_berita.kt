package com.example.zhack.share_ie.berita

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.tv.TvContract
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.ActivityCompat.finishAffinity
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import com.example.zhack.share_ie.API.*
import com.example.zhack.share_ie.Login
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.UI.create_status
import com.example.zhack.share_ie.komentar.Komentar
import com.example.zhack.share_ie.komentar.adapterKomentar
import com.example.zhack.share_ie.model.*
import com.lapism.searchview.Search
import com.lapism.searchview.widget.SearchBar
import com.squareup.picasso.Picasso
import com.yalantis.phoenix.PullToRefreshView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.berita.*
import kotlinx.android.synthetic.main.design_bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.floating.*
import kotlinx.android.synthetic.main.tampilan_berita.view.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.jetbrains.anko.AlertBuilder
import org.jetbrains.anko.actionMenuView
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class control_berita : Fragment(), AdapterRv.Listener {

    private var mCompositeDisposable:CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataBerita>?=null
    public var mListKomentar:ArrayList<DataKomentar>?=null
    private var madapter: AdapterRv? = null
    public var adapter:adapterKomentar? = null
    var sessionManagment:SessionManagment? = null
    private lateinit var pullToRefreshView: PullToRefreshView

    override fun onItemClick(view:View) {
        view.komentar.setOnClickListener {
            val komentar = Komentar()
            komentar.show(fragmentManager?.beginTransaction(),komentar.tag)

        }
    }

//    override fun onResume() {
//        super.onResume()
//        shimmer.startShimmerAnimation()
//    }

    override fun onPause() {
        super.onPause()
        shimmer.stopShimmerAnimation()
    }

    override fun onCreateView(inflater: LayoutInflater,
                                       container: ViewGroup?,
                                       savedInstanState: Bundle?) = inflater.inflate(R.layout.berita,container,false)
         override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
             sessionManagment = SessionManagment(context)
             sessionManagment!!.checkLogin()
             super.onViewCreated(view, savedInstanceState)
             mCompositeDisposable = CompositeDisposable()

             try{
                 shimmer.startShimmerAnimation()
                 initRecyclerView()
                 callWebService()
                 search()
                 search_bar.setLogoIcon(R.drawable.search)
                 offset(0)
                 pull_refresh.setOnRefreshListener {
                     pull_refresh.postDelayed(Runnable {
                         showList()
                         callWebService()
                         text_gone.visibility = View.INVISIBLE
                         shimmer_layout.visibility = View.VISIBLE
                         shimmer.startShimmerAnimation()
                     }, 2000)
                 }

             }catch (e:Exception){
                 e.message
             }
//             fungsiClick()
             upload_berita.setOnClickListener{
                 val inn = Intent(context,create_status::class.java)
                 startActivity(inn)
             }
         }

    private fun offset(vertical:Int){
        if(scroll.verticalScrollbarPosition == vertical){
            pull_refresh.setRefreshing(false)
        }else{
            pull_refresh.setRefreshing(true)
        }
    }
    private fun callWebService(){

        val retrofit = ApiClient.create()
        val panggil = retrofit.getDetail(sessionManagment!!.UserId(),sessionManagment!!.UserToken())
        panggil.enqueue(object :Callback<status>{
            override fun onFailure(call: Call<status>, t: Throwable) {
//                    Toast.makeText(this@Login,t.toString(),Toast.LENGTH_LONG).show()
                Log.e("pesan",t.toString())
            }
            override fun onResponse(call: Call<status>, response: Response<status>) {
                if (response.isSuccessful) {
                    val datum = response.body()!!.detail
                    if (response.body()!!.status!!.equals(200)) {

                        var getUserDetail = retrofit.getUserDetail(sessionManagment!!.UserId(),sessionManagment!!.UserToken(), sessionManagment!!.UserId().toInt())
                        getUserDetail.enqueue(object : Callback<status_user_detail> {
                            override fun onFailure(call: Call<status_user_detail>, t: Throwable) {

                            }

                            override fun onResponse(call: Call<status_user_detail>, response: Response<status_user_detail>) {
                                if (response.isSuccessful) {
                                    var res = response.body()!!.detail
                                    res.map {
//                                        val user = retrofit.getUserAll(it.user_id, sessionManagment!!.UserToken(), Integer.parseInt(sessionManagment!!.UserId()))
//                                        Log.d("Main aktifitas", sessionManagment!!.UserToken())
//                                        user.enqueue(object : Callback<user_detail>{
//                                            override fun onFailure(call: Call<user_detail>, t: Throwable) {
//
//                                            }
//
//                                            override fun onResponse(call: Call<user_detail>, response: Response<user_detail>) {
//                                                if (response.isSuccessful) {
//                                                    Log.d("Main aktifitas", response.body()!!.username)
//                                                    var bodi = response.body()
//                                                    sessionManagment!!.createUserDetail(bodi!!.username, bodi!!.name, bodi!!.foto, bodi!!.email, bodi!!.no_hp)
//                                                }
//                                            }
//
//                                        })
                                        sessionManagment!!.createUserDetail(it.username,it.name,it.foto, it.email, it.no_hp)
                                            Log.d("cek_error", it.foto)
                                    }
                                }else{
                                    Log.d("cek_error",response.body()!!.message)
                                }
                            }

                        })

                        var list:List<DataBerita> = response.body()!!.detail
                        mAndroidList = ArrayList(list)
                        madapter = AdapterRv(mAndroidList!!, this@control_berita)
                        datum.map {

                        }
                        if(mAndroidList!!.count() >0){
                            try{
                                text_gone.visibility = View.INVISIBLE
                                rv.adapter = madapter
                                rv.visibility = View.VISIBLE
                                shimmer.stopShimmerAnimation()
                                shimmer_layout.visibility = View.INVISIBLE
                            }catch (e:Exception){
                                e.message
                            }
                        }else{
                            try {
                                text_gone.visibility = View.VISIBLE
                                shimmer.stopShimmerAnimation()
                                shimmer_layout.visibility = View.INVISIBLE
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

        rv.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager
    }

    fun showList(){
        mAndroidList?.clear()
        mAndroidList?.addAll(mAndroidList!!)
        madapter?.notifyDataSetChanged()
        pull_refresh.setRefreshing(false)
    }


    private fun search(){
        search_bar.setOnQueryTextListener(object : Search.OnQueryTextListener{
            override fun onQueryTextSubmit(query: CharSequence?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: CharSequence?) {
                Log.d("searchbar",newText.toString())
                madapter!!.filter.filter(newText)
            }

        })
    }

//    fun loadData(){
//        val cacheSize = 5*1024*1024
//        val cache = Cache(context!!.cacheDir,cacheSize.toLong())
//    }

}