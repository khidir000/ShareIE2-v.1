package com.example.zhack.share_ie.berita

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.API.DataBerita
import com.example.zhack.share_ie.API.DataInterface
import com.example.zhack.share_ie.API.DataKomentar
import com.example.zhack.share_ie.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.komentar.view.*
import kotlinx.android.synthetic.main.tampilan_berita.view.*
import org.jetbrains.anko.image


class AdapterRv(private val dataList:ArrayList<DataBerita>
                ,private val listener: Listener)//ArrayList<DataInterface>
    :RecyclerView.Adapter<AdapterRv.DataHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.tampilan_berita,parent,false)
        return DataHolder(view)
    }

    interface Listener{
        fun onItemClick(view:View)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(dataList[position],listener,position)
    }

    class DataHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(android: DataBerita, listener: Listener, position: Int){
            itemView.nama_user.text = android.name
            itemView.body_status.text = android.isi_berita
            itemView.id_user.text  = android.username
            itemView.text_waktu.text = android.create_at
            val image:CircleImageView = itemView.findViewById(R.id.imagecircle)
            if(android.foto==""){
                image.setImageResource(R.mipmap.ic_launcher)
            }else{
                Picasso.get().load(android.foto).into(image)
            }
            itemView.setOnClickListener{listener.onItemClick(itemView)}
        }
    }
}