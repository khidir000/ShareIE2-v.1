package com.example.zhack.share_ie.berita

import android.content.Context
import android.net.sip.SipSession
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zhack.share_ie.API.DataCategory
import com.example.zhack.share_ie.API.DataInterface
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.API.RequestData
import com.example.zhack.share_ie.komentar.Komentar
import com.yalantis.phoenix.PullToRefreshView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.extensions.ContainerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.berita.*
import kotlinx.android.synthetic.main.design_bottom_sheet_dialog.*
import kotlinx.android.synthetic.main.tampilan_berita.*
import kotlinx.android.synthetic.main.tampilan_berita.view.*
import okhttp3.Cache
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class control_berita : Fragment(), AdapterRv.Listener {

    private var mCompositeDisposable:CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataInterface>?=null
    private var madapter: AdapterRv? = null
    private lateinit var pullToRefreshView: PullToRefreshView

    override fun onItemClick(view: View) {
        view.komentar.setOnClickListener {
//            val view = layoutInflater.inflate(R.layout.komentar,null)
//            val dialog = BottomSheetDialog(requireContext())
//            dialog.setContentView(view)
//            dialog.show()
            val komentar = Komentar()
            komentar.show(fragmentManager?.beginTransaction(),komentar.tag)
        }
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
        val apiService = RequestData.create()

        val call = apiService.getItemDetail()

        call.enqueue(object: Callback<DataCategory> {
            override fun onFailure(call: Call<DataCategory>, t: Throwable) {
            }

            override fun onResponse(call: Call<DataCategory>, response: Response<DataCategory>) {
                if (response!=null){
                    var list:List<DataInterface> = response.body()!!.teams
                    mAndroidList = ArrayList(list)
                    madapter = AdapterRv(mAndroidList!!, this@control_berita)
                    rv.adapter = madapter
                    rv.visibility = View.VISIBLE
                    shimmer.stopShimmerAnimation()
                    shimmer_layout.visibility = View.INVISIBLE
                }
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