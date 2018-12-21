package com.example.zhack.share_ie.komentar


import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.komentar.adapterKomentar
import com.example.zhack.share_ie.model.DataKomentar
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.komentar.*


class Komentar : BottomSheetDialogFragment() {
    private var mCompositeDisposable: CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataKomentar>?=null
    private var madapter: adapterKomentar?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?):View{
        return layoutInflater.inflate(R.layout.komentar,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        calling()

    }

    private fun calling(){

//        val httpClient = OkHttpClient().newBuilder()
//        val interceptor = Interceptor { chain ->
//            val request = chain?.request()?.newBuilder()?.header("Client-Service","frontend-client")?.header("Auth-Key","simplerestapi")?.build()
//            chain?.proceed(request)
//        }
//        httpClient.networkInterceptors().add(interceptor)
//        val apiService = ApiClient.create()
//
//        val call = apiService.getKoment("1","$1$.WTaRTC4"+"$"+"h1gvoffq..oHft1eslTeS1")
//
//        call.enqueue(object: Callback<List<DataKomentar>> {
//            override fun onFailure(call: Call<List<DataKomentar>>, t: Throwable) {
//            }
//
//            override fun onResponse(call: Call<List<DataKomentar>>, response: Response<List<DataKomentar>>) {
//                if (response!=null){
//                    var list:List<DataKomentar> = response.body()!!
//                    mAndroidList = ArrayList(list)
//                    madapter = adapterKomentar(mAndroidList!!)
//                    rv_komen.adapter = madapter
//                    jml_komen.setText(list.size.toString())
//
//                }else{
//                    Toast.makeText(context,"gagal",Toast.LENGTH_LONG).show()
//                }
//            }
//        })

    }

    private fun initRecyclerView() {

        rv_komen.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv_komen.layoutManager = layoutManager
    }


}
