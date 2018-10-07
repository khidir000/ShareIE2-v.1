package com.example.zhack.share_ie.berita

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.API.DataCategory
import com.example.zhack.share_ie.API.DataInterface
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.API.RequestData
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.berita.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class control_berita : Fragment(), AdapterRv.Listener {

    private var mCompositeDisposable:CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataInterface>?=null
    private var madapter: AdapterRv? = null

    override fun onItemClick(android: DataInterface) {

    }


    override fun onCreateView(inflater: LayoutInflater,
                                       container: ViewGroup?,
                                       savedInstanState: Bundle?) = inflater.inflate(R.layout.berita,container,false)
         override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
             super.onViewCreated(view, savedInstanceState)

             mCompositeDisposable = CompositeDisposable()
             initRecyclerView()
             callWebService()
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

                }
            }
        })
    }

    private fun initRecyclerView() {

        rv.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager
    }
}