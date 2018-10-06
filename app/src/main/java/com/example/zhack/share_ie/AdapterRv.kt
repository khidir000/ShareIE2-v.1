package com.example.zhack.share_ie

import android.net.sip.SipSession
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.tampilan_berita.view.*


class AdapterRv(private val dataList:ArrayList<DataInterface>
                ,private val listener: Listener)
    :RecyclerView.Adapter<AdapterRv.DataHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.tampilan_berita,parent,false)
        return DataHolder(view)
    }

    interface Listener{
        fun onItemClick(android:DataInterface)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(dataList[position],listener,position)
    }

    class DataHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(android:DataInterface, listener:Listener, position: Int){
            itemView.nama_user.text = android.teamName
            itemView.body_status.text = android.strDescriptionEN
            val image:CircleImageView = itemView.findViewById(R.id.imagecircle)
            Picasso.get().load(android.teamBadge).into(image)

            itemView.setOnClickListener{listener.onItemClick(android)}
        }
    }
}