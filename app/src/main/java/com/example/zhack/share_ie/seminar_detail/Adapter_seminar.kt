package com.example.zhack.share_ie.seminar_detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.model.seminar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.seminar.view.*

class Adapter_seminar(private var data:ArrayList<seminar>,private var listener:Listener):RecyclerView.Adapter<Adapter_seminar.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.seminar, parent,false)
        return Holder(view)
    }

    interface Listener{
        fun onItemClick(view: View, position: Int)
    }
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(data[position],listener,position)
    }

    class Holder(view:View):RecyclerView.ViewHolder(view){
        fun bind(android:seminar,listener:Listener,position: Int){
            itemView.tanggal.text = android.tanggal
            itemView.jam.text = android.waktu
            itemView.nama_user_seminar.text = android.name
            itemView.nim_user_seminar.text = android.username
            itemView.judul_seminar.text = android.judul
            itemView.lokasi.text = android.lokasi
            var image = itemView.findViewById<ImageView>(R.id.image)
            Picasso.get().load(android.foto).into(image)
            itemView.fab_dosen_seminar.setOnClickListener { listener.onItemClick(it, position) }

        }
    }
}