package com.example.zhack.share_ie.berita

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zhack.share_ie.API.*
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.komentar.adapterKomentar
import com.example.zhack.share_ie.model.DataBerita
import com.example.zhack.share_ie.model.DataKomentar
import com.yalantis.phoenix.PullToRefreshView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.berita.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class control_berita : Fragment(), AdapterRv.Listener {

    private var mCompositeDisposable:CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataBerita>?=null
    public var mListKomentar:ArrayList<DataKomentar>?=null
    private var madapter: AdapterRv? = null
    public var adapter:adapterKomentar? = null
    private lateinit var pullToRefreshView: PullToRefreshView

    override fun onItemClick(view:View) {
        view.setOnClickListener {
            Toast.makeText(context,it.berita.toString(), Toast.LENGTH_LONG)
        }
//        view.komentar.setOnClickListener {
//            val komentar = Komentar()
//            komentar.show(fragmentManager?.beginTransaction(),komentar.tag)
//
//        }
    }

    override fun onResume() {
        super.onResume()
        shimmer.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        shimmer.stopShimmerAnimation()
    }

    override fun onCreateView(inflater: LayoutInflater,
                                       container: ViewGroup?,
                                       savedInstanState: Bundle?) = inflater.inflate(R.layout.berita,container,false)
         override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
             super.onViewCreated(view, savedInstanceState)
             mCompositeDisposable = CompositeDisposable()
             initRecyclerView()
             callWebService()
             offset(0)
             pull_refresh.setOnRefreshListener{
                 pull_refresh.postDelayed(Runnable {
                     showList()
                     callWebService()
                     shimmer_layout.visibility = View.VISIBLE
                     shimmer.startShimmerAnimation()
                 },2000)
             }
//             fungsiClick()
         }

    private fun offset(vertical:Int){
        if(scroll.verticalScrollbarPosition == vertical){
            pull_refresh.setRefreshing(false)
        }else{
            pull_refresh.setRefreshing(true)
        }
    }
    private fun callWebService(){
        val httpClient = OkHttpClient().newBuilder()
        val interceptor = Interceptor { chain ->
            val request = chain?.request()?.newBuilder()?.header("Client-Service","frontend-client")?.header("Auth-Key","simplerestapi")?.build()
            chain?.proceed(request)
        }
        httpClient.networkInterceptors().add(interceptor)
        val retrofit = ApiClient.create()
        val panggil = retrofit.getDetail("1","$1$.WTaRTC4"+"$"+"h1gvoffq..oHft1eslTeS1")
        panggil.enqueue(object :Callback<List<DataBerita>>{
            override fun onFailure(call: Call<List<DataBerita>>, t: Throwable) {
//                    Toast.makeText(this@Login,t.toString(),Toast.LENGTH_LONG).show()
                Log.e("pesan",t.toString())
            }
            override fun onResponse(call: Call<List<DataBerita>>, response: Response<List<DataBerita>>) {
                if (response.isSuccessful) {
                    val datum = response.body()
                    var list:List<DataBerita> = response.body()!!
                    mAndroidList = ArrayList(list)

                    datum?.map {
//                        Log.d("pesan","datanya ${it.komentar[0].isi_komentar}")
//                        var list_data:List<DataKomentar> = listOf(it.komentar[0])
//                        mListKomentar = ArrayList(list_data)
                        //Log.d("pesan", ""+ mListKomentar)
                    }
                    madapter = AdapterRv(mAndroidList!!, this@control_berita)
                    rv.adapter = madapter
                    rv.visibility = View.VISIBLE
                    shimmer.stopShimmerAnimation()
                    shimmer_layout.visibility = View.INVISIBLE
                }else{

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
    

//    fun loadData(){
//        val cacheSize = 5*1024*1024
//        val cache = Cache(context!!.cacheDir,cacheSize.toLong())
//    }

}