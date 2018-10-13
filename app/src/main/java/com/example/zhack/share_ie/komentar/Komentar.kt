package com.example.zhack.share_ie.komentar


import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.API.DataCategory
import com.example.zhack.share_ie.API.DataInterface
import com.example.zhack.share_ie.API.RequestData
import com.example.zhack.share_ie.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.komentar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Komentar : BottomSheetDialogFragment() {
    private var mCompositeDisposable: CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataInterface>?=null
    private var madapter: adapterKomentar? = null

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
        val apiService = RequestData.create()

        val call = apiService.getItemDetail()

        call.enqueue(object: Callback<DataCategory> {
            override fun onFailure(call: Call<DataCategory>, t: Throwable) {
            }

            override fun onResponse(call: Call<DataCategory>, response: Response<DataCategory>) {
                if (response!=null){
                    var list:List<DataInterface> = response.body()!!.teams
                    mAndroidList = ArrayList(list)
                    madapter = adapterKomentar(mAndroidList!!)
                    rv_komen.adapter = madapter
                    jml_komen.setText(list.size.toString())

                }
            }
        })
    }

    private fun initRecyclerView() {

        rv_komen.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv_komen.layoutManager = layoutManager
    }


}
