package com.example.zhack.share_ie.UI

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.model.dosen_seminar_model
import kotlinx.android.synthetic.main.adapter_dosen_smr.view.*


class adapter_seminar_dosen(private var data:ArrayList<dosen_seminar_model>, private var listener: Listener): RecyclerView.Adapter<adapter_seminar_dosen.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_dosen_smr, parent,false)
        return adapter_seminar_dosen.Holder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position],listener,position)
    }

    interface Listener{
        fun onItemClick(view: View, position: Int)
    }



    class Holder(view: View):RecyclerView.ViewHolder(view){
        fun bind(android: dosen_seminar_model, listener: Listener, position: Int){

            itemView.nama_dosen_smr.text = android.dosen
            itemView.jenis_dosen_smr.text = android.jenis
        }
    }
}