package com.example.zhack.share_ie.UI

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.seminar_detail.seminar_detail
import com.example.zhack.share_ie.model.seminar
import com.example.zhack.share_ie.model.status_seminar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_seminar.*
import kotlinx.android.synthetic.main.list_seminar_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class seminar_list:Fragment(){

    var session:SessionManagment?=null
    private var mAndroidList:ArrayList<seminar>?=null
    private var madapter: AdapterRv?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.list_seminar,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        session = SessionManagment(context)
        session!!.checkLogin()
        super.onViewCreated(view, savedInstanceState)
        recycler_list()
        initRecyclerView()

        proposal_list.setOnClickListener {
            Log.d("tombol_proposal","error")
            var intent = Intent(context, seminar_detail::class.java)
            intent.putExtra("key","proposal")
            context!!.startActivity(intent)
        }

        hasil_list.setOnClickListener {
            var intent = Intent(context, seminar_detail::class.java)
            intent.putExtra("key","hasil")
            context!!.startActivity(intent)
        }

        ujian_list.setOnClickListener {
            var intent = Intent(context, seminar_detail::class.java)
            intent.putExtra("key","ujian")
            context!!.startActivity(intent)
        }
    }

    private fun recycler_list(){

        var api = ApiClient.create()

        var retofit = api.getSeminarAll(session!!.UserId(),session!!.UserToken())
        retofit.enqueue(object : Callback<status_seminar>{
            override fun onFailure(call: Call<status_seminar>, t: Throwable) {

            }

            override fun onResponse(call: Call<status_seminar>, response: Response<status_seminar>) {
                if (response.isSuccessful) {
                    var detail = response.body()!!.detail
                    detail.map {
                        if(it.jenis!!.equals("PROPOSAL")){
                            var api1 = ApiClient.create()
                            var retro2 = api1.getSeminar(session!!.UserId(),session!!.UserToken(),"proposal")
                            retro2.enqueue(object : Callback<status_seminar>{
                                override fun onFailure(call: Call<status_seminar>, t: Throwable) {

                                }

                                override fun onResponse(call: Call<status_seminar>, response: Response<status_seminar>) {
                                    if (response.isSuccessful) {
                                        try {
                                            var list:List<seminar> = response.body()!!.detail
                                            mAndroidList = ArrayList(list)
                                            madapter = AdapterRv(mAndroidList!!)
                                            proposal.adapter = madapter
                                        }catch (e:Exception){
                                            e.message
                                        }
                                    }
                                }

                            })
                        }else if(it.jenis!!.equals("HASIL")){
                            var api1 = ApiClient.create()
                            var retro2 = api1.getSeminar(session!!.UserId(),session!!.UserToken(),"hasil")
                            retro2.enqueue(object : Callback<status_seminar>{
                                override fun onFailure(call: Call<status_seminar>, t: Throwable) {

                                }

                                override fun onResponse(call: Call<status_seminar>, response: Response<status_seminar>) {
                                    if (response.isSuccessful) {
                                        try {
                                            var list:List<seminar> = response.body()!!.detail
                                            mAndroidList = ArrayList(list)
                                            madapter = AdapterRv(mAndroidList!!)
                                            hasil.adapter = madapter
                                        }catch (e:Exception){
                                            e.message
                                        }
                                    }
                                }

                            })
                        }else{
                            var api1 = ApiClient.create()
                            var retro2 = api1.getSeminar(session!!.UserId(),session!!.UserToken(),"ujian")
                            retro2.enqueue(object : Callback<status_seminar>{
                                override fun onFailure(call: Call<status_seminar>, t: Throwable) {

                                }

                                override fun onResponse(call: Call<status_seminar>, response: Response<status_seminar>) {
                                    if (response.isSuccessful) {
                                        try {
                                            var list:List<seminar> = response.body()!!.detail
                                            mAndroidList = ArrayList(list)
                                            madapter = AdapterRv(mAndroidList!!)
                                            ujian.adapter = madapter
                                        }catch (e:Exception){
                                            e.message
                                        }

                                    }
                                }

                            })
                        }
                    }
                }

            }

        })
    }

    private class AdapterRv(private val dataList:ArrayList<seminar>)//ArrayList<DataInterface>
        :RecyclerView.Adapter<AdapterRv.DataHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_seminar_detail,parent,false)
            return DataHolder(view)
        }

        override fun getItemCount(): Int = dataList.size

        override fun onBindViewHolder(holder: DataHolder, position: Int) {
            holder.bind(dataList[position],position)
        }

        class DataHolder(view: View):RecyclerView.ViewHolder(view){
            fun bind(android: seminar,  position: Int){
                itemView.nama_user_seminar_detail.text = android.name
                itemView.nim_user_seminar_detail.text = android.username
                itemView.waktu_seminar_detail.text = "waktu : "+android.tanggal
                val image: ImageView = itemView.findViewById(R.id.gambar_user_seminar_detail)
                if(android.foto==""){
                    image.setImageResource(R.drawable.user)
                }else{
                    Picasso.get().load(android.foto).into(image)
                }

            }
        }
    }

    private fun initRecyclerView() {

        proposal.setHasFixedSize(true)
        hasil.setHasFixedSize(true)
        ujian.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        proposal.layoutManager = layoutManager
        val layoutManager2 : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        hasil.layoutManager = layoutManager2
        val layoutManager3 : RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        ujian.layoutManager = layoutManager3
    }

}