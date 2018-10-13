package com.example.zhack.share_ie.komentar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.API.DataInterface
import com.example.zhack.share_ie.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.komentar.view.*
import kotlinx.android.synthetic.main.komentar_isi.view.*

class adapterKomentar(val dataKomentar: ArrayList<DataInterface>)
    :RecyclerView.Adapter<adapterKomentar.KomentarHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomentarHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.komentar_isi,parent,false)
        return KomentarHolder(view)
    }

    override fun getItemCount(): Int = dataKomentar.size

    override fun onBindViewHolder(holder: KomentarHolder, position: Int) {
        holder.onBind(dataKomentar[position])
    }

    class KomentarHolder(view: View):RecyclerView.ViewHolder(view){
        fun onBind(komentar:DataInterface){
            itemView.user_komen.text = komentar.teamName
            itemView.body_komen.text = komentar.strDescriptionEN
            Picasso.get().load(komentar.teamBadge).into(itemView.image_komen)
        }
    }
}