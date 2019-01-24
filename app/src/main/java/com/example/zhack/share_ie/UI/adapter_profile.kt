package com.example.zhack.share_ie.UI

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.model.DataBerita
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.profil_berita_isi.view.*

class adapter_profile(private var dataList:ArrayList<DataBerita>
                ,private var listener: Listener)//ArrayList<DataInterface>
    : RecyclerView.Adapter<adapter_profile.DataHolder>() {
    private var sessionManagment:SessionManagment?=null
    private var context:Context?=null
    private var datalis:ArrayList<DataBerita>? = dataList
    private var datbaru:ArrayList<DataBerita> = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.profil_berita_isi,parent,false)
        return DataHolder(view)
    }

    interface Listener{
        fun onItemClick(view: View)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(dataList[position],listener,position)

    }

    override fun getItemCount(): Int = dataList.size


    class DataHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(android: DataBerita, listener: Listener, position: Int){
                itemView.nama_user_profile.text = android.name
                itemView.body_status_profile.text = android.isi_berita
                itemView.id_user_profile.text  = android.username
                itemView.text_waktu_profile.text = android.create_at
                val count_komen = android.komentar.size
                itemView.count_komen_profile.text = count_komen.toString()
                val image: CircleImageView = itemView.findViewById(R.id.imagecircle_profile)
                if(android.foto==""){
                    image.setImageResource(R.drawable.user)
                }else{
                    Picasso.get().load(android.foto).into(image)
                }
                itemView.setOnClickListener{listener.onItemClick(itemView)}
        }
    }

}