package com.example.zhack.share_ie.komentar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.model.DataKomentar
import com.example.zhack.share_ie.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.komentar_isi.view.*

class adapterKomentar(val dataKomentar: ArrayList<DataKomentar>)
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
        fun onBind(komentar: DataKomentar){
            itemView.user_komen.text = komentar.name
            itemView.body_komen.text = komentar.isi_komentar
            val image_komen:CircleImageView = itemView.findViewById(R.id.image_komen)
            if(komentar.foto==""){
                image_komen.setImageResource(R.mipmap.ic_launcher)
            }else{
                Picasso.get().load(komentar.foto).into(image_komen)
            }
        }
    }
}