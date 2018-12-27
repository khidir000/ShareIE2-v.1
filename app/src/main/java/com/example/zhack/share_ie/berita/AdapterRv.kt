package com.example.zhack.share_ie.berita

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.example.zhack.share_ie.model.DataBerita
import com.example.zhack.share_ie.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.tampilan_berita.view.*


class AdapterRv(private var dataList:ArrayList<DataBerita>
                ,private var listener: Listener)//ArrayList<DataInterface>
    :RecyclerView.Adapter<AdapterRv.DataHolder>(), Filterable {
    private var datalis:ArrayList<DataBerita>? = dataList
    private var datbaru:ArrayList<DataBerita> = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.tampilan_berita,parent,false)
        return DataHolder(view)
    }

    interface Listener{
        fun onItemClick(view:View)
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {
        holder.bind(dataList[position],listener,position)
    }

    override fun getItemCount(): Int = dataList.size


    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence): FilterResults {
                var searchString = p0.toString()
                if(searchString.isEmpty()){
                    dataList = datbaru
                }else{
                    var temp = ArrayList<DataBerita>()

                    datbaru!!.forEach{
                        if(it.isi_berita!!.toLowerCase().contains(searchString)){
                            temp.add(it)
                            Log.d("cek_bisa",it.isi_berita)
                        }
                    }
                    dataList = temp
                    Log.d("isi_datalis",dataList.toString())
                }

                var filterResults = FilterResults()
                filterResults!!.values = dataList
                Log.d("filterres",filterResults.toString())
                return filterResults
            }

            override fun publishResults(p0: CharSequence, filterResults: FilterResults?) {
                dataList = filterResults?.values as ArrayList<DataBerita>
                Log.d("publihfilter",datalis.toString())
                notifyDataSetChanged()
            }

        }
    }


    class DataHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(android: DataBerita, listener: Listener, position: Int){
            itemView.nama_user.text = android.name
            itemView.body_status.text = android.isi_berita
            itemView.id_user.text  = android.username
            itemView.text_waktu.text = android.create_at
            val count_komen = android.komentar.size
            itemView.count_komen.text = count_komen.toString()
            val image:CircleImageView = itemView.findViewById(R.id.imagecircle)
            if(android.foto==""){
                image.setImageResource(R.drawable.user)
            }else{
                Picasso.get().load(android.foto).into(image)
            }


            itemView.setOnClickListener{listener.onItemClick(itemView)}
        }
    }
}