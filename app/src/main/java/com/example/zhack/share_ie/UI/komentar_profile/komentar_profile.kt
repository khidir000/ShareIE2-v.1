package com.example.zhack.share_ie.UI.komentar_profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.R.id.*
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.komentar.Komentar
import com.example.zhack.share_ie.komentar.adapterKomentar
import com.example.zhack.share_ie.model.DataBerita
import com.example.zhack.share_ie.model.DataKomentar
import com.example.zhack.share_ie.model.komen_status
import com.example.zhack.share_ie.model.status
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.komentar.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.FieldPosition

@SuppressLint("ValidFragment")
class komentar_profile(position: Int): BottomSheetDialogFragment(), ak_profile.Listener{
    private var mCompositeDisposable: CompositeDisposable?=null
    private var mAndroidList:ArrayList<DataKomentar>?=null
    private var madapter: ak_profile?=null
    private var hashMap = HashMap<Int, String>()
    private var sessionManagment: SessionManagment? = null
    private var posisi:Int = position

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.komentar,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManagment = SessionManagment(context)
        mCompositeDisposable = CompositeDisposable()
        initRecyclerView()
        calling()
        et_komentar_btn.setOnClickListener {
            try{
                if(et_komentar.text.toString()!=""){
                    prog.visibility = View.VISIBLE
                    rv_komen.visibility = View.GONE
                    Log.d("btn_komen", posisi.toString()+" "+hashMap[posisi].toString())
                    var api = ApiClient.create()
                    var client = api.getPostKomen(sessionManagment!!.UserId(),sessionManagment!!.UserToken(), komen_status(hashMap[posisi]!!.toInt(),et_komentar.text.toString(),sessionManagment!!.UserId().toInt()))
                    client.enqueue(object : Callback<status> {
                        override fun onFailure(call: Call<status>, t: Throwable) {
                            Log.d("btn_komen", t.message.toString())
                            prog.visibility = View.GONE
                            Toast.makeText(context,"gagal", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<status>, response: Response<status>) {
                            if (response.isSuccessful) {
                                Log.d("btn_komen", response.body()!!.status.toString())
                                showList()
                                calling()
                                prog.visibility = View.GONE
                                rv_komen.visibility = View.VISIBLE
                            }
                        }

                    })
                    et_komentar.text.clear()
                }
            }catch (e: Exception){

            }
        }

    }

    override fun onItemClick(view: View, position: Int) {
    }

    public fun calling(){

        var retro = ApiClient.create()
        var ret = retro.getBeritaDetail(sessionManagment!!.UserId(),sessionManagment!!.UserToken(), sessionManagment!!.UserId().toInt())
        ret.enqueue(object : Callback<status> {
            override fun onFailure(call: Call<status>, t: Throwable) {

            }

            override fun onResponse(call: Call<status>, response: Response<status>) {
                if (response.isSuccessful) {
                    var list:List<DataBerita> = response.body()!!.detail
                    list.map {
                        try {
                            var l:List<DataKomentar> = list.get(posisi).komentar
                            for(i in 0..list.count()-1){
                                hashMap[i] = list.get(i).id_berita.toString()
                            }
                            Log.d("posisi", posisi.toString()+" "+hashMap[posisi].toString())
                            if(l!=null){
                                jml_komen.text = l.count().toString()
                                mAndroidList = ArrayList(l)
                                madapter = ak_profile(mAndroidList,this@komentar_profile)
                                rv_komen.adapter = madapter
                                madapter!!.notifyDataSetChanged()
                                prog.visibility = View.GONE
                                rv_komen.visibility = View.VISIBLE
                            }else{
                                Log.d("control_berita","error disono")
                            }

                        }catch (e: Exception){
                            e.stackTrace
                        }
                        Log.d("control_berita",it.komentar.toString())
                    }
                }
            }

        })


    }

    private fun initRecyclerView() {

        rv_komen.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        rv_komen.layoutManager = layoutManager
    }

    fun showList(){
        mAndroidList?.clear()
        mAndroidList?.addAll(mAndroidList!!)
        madapter?.notifyDataSetChanged()
    }


}