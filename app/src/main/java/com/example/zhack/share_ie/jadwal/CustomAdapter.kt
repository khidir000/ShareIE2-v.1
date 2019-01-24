package com.example.zhack.share_ie.jadwal

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.zhouchaoyuan.excelpanel.BaseExcelPanelAdapter
import com.example.zhack.share_ie.R
import kotlinx.android.synthetic.main.item_jadwal.view.*

class CustomAdapter(var context: Context?): BaseExcelPanelAdapter<Toptitle,LeftTitle,cell>(context){
    override fun onCreateTopViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_jadwal,parent,false)
        return TopHolder(view)
    }

    class TopHolder(view: View?) : RecyclerView.ViewHolder(view) {

    }

    override fun onBindTopViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var topTitle = getTopItem(position)
        holder!!.itemView!!.item_text.text = topTitle.title
    }



    override fun onCreateLeftViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_jadwal,parent,false)
        return LeftHolder(view)
    }

    override fun onBindLeftViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        var topTitle = getLeftItem(position)
        holder!!.itemView!!.item_text.text = topTitle.title
    }
    class LeftHolder(view: View?) : RecyclerView.ViewHolder(view) {

    }



    override fun onCreateCellViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_jadwal,parent,false)
        return CellHolder(view)
    }



    override fun onBindCellViewHolder(holder: RecyclerView.ViewHolder?, verticalPosition: Int, horizontalPosition: Int) {
        var cellTitle = getMajorItem(verticalPosition,horizontalPosition)
        holder!!.itemView!!.item_text.text = cellTitle.title
    }
    class CellHolder(view: View?) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateTopLeftView(): View {
        return LayoutInflater.from(context).inflate(R.layout.item_jadwal,null)
    }
}