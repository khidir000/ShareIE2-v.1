package com.example.zhack.share_ie.jadwal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.ListAdapter
import android.widget.Toast
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.model.detail_jadwal
import com.example.zhack.share_ie.model.status_jadwal
import kotlinx.android.synthetic.main.jadwal.*
import org.jetbrains.anko.support.v4.find
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.HashMap

class jadwal : Fragment() {

    private var listAdapter: ExpandableListAdapter? = null
    private var listDataHeader: ArrayList<String>? = null
    private var listDataChild =  HashMap<String, List<String>>()
    private var sessionManagment:SessionManagment?=null
    private val client = ApiClient.create()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View = inflater.inflate(R.layout.jadwal,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManagment = SessionManagment(context)
        prepareListData()

        listAdapter = CustomExpand(this!!.context!!, this!!.listDataHeader!!,listDataChild)
        lvExp.setAdapter(listAdapter)

        lvExp.setOnGroupClickListener { expandableListView, view, i, l -> false}

        lvExp.setOnGroupExpandListener(object : ExpandableListView.OnGroupExpandListener{
            override fun onGroupExpand(p0: Int) {

            }

        })

        lvExp.setOnGroupCollapseListener(object : ExpandableListView.OnGroupCollapseListener{
            override fun onGroupCollapse(p0: Int) {

            }

        })
    }

    private fun prepareListData() {
        listDataHeader = ArrayList()
        listDataChild = HashMap()

        // Adding child data
        listDataHeader?.add("Senin")
        listDataHeader?.add("Selasa")
        listDataHeader?.add("Rabu")
        listDataHeader?.add("Kamis")
        listDataHeader?.add("Jum'at")
        listDataHeader?.add("Sabtu")


        // Adding child data
        var Senin = ArrayList<String>()
        var Selasa = ArrayList<String>()
        var Rabu = ArrayList<String>()
        var Kamis = ArrayList<String>()
        var Jumat = ArrayList<String>()
        var Sabtu = ArrayList<String>()

        var senin = client.getJadwal(sessionManagment!!.UserId(),sessionManagment!!.UserToken())
        senin.enqueue(object : Callback<status_jadwal>{
            override fun onFailure(call: Call<status_jadwal>, t: Throwable) {

            }

            override fun onResponse(call: Call<status_jadwal>, response: Response<status_jadwal>) {
                if (response.isSuccessful) {
                    var bodi = response.body()?.detail
                    for(i in 0..bodi!!.count()-1){
                        var list:String = bodi!![i].nama_mk.toString()+" ( ${bodi!![i].jam_mulai.toString()} - ${bodi!![i].jam_berakhir.toString()} ) \n ${bodi!![i].ruangan_mk.toString()} \n ${bodi!![i].dosen_mk.toString()}"
                        var hari:String = bodi!![i].day.toString()
                        if(hari.equals("SENIN")){
                            Senin.add(list)
                        }else if(hari.equals("SELASA")){
                            Selasa.add(list)
                        }
                        else if(hari.equals("RABU")){
                            Rabu.add(list)
                        }
                        else if(hari.equals("KAMIS")){
                            Kamis.add(list)
                        }
                        else if(hari.equals("JUM'AT")){
                            Jumat.add(list)
                        }
                        else if(hari.equals("SABTU")){
                            Sabtu.add(list)
                        }
                    }
                }
            }

        })

        listDataChild[listDataHeader!!.get(0)] = Senin // Header, Child data
        listDataChild[listDataHeader!!.get(1)] = Selasa
        listDataChild[listDataHeader!!.get(2)] = Rabu // Header, Child data
        listDataChild[listDataHeader!!.get(3)] = Kamis
        listDataChild[listDataHeader!!.get(4)] = Jumat // Header, Child data
        listDataChild[listDataHeader!!.get(5)] = Sabtu
//        listDataChild[listDataHeader!!.get(2)] = comingSoon

    }

   }
