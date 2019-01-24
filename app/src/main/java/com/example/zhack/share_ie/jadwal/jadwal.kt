package com.example.zhack.share_ie.jadwal

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.R
import kotlinx.android.synthetic.main.jadwal.*
import java.util.*

data class Toptitle(var title:String? =null)
data class LeftTitle(var title: String?=null)
data class cell(var title: String? = null)
class jadwal : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.jadwal,container,false)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var adapter = CustomAdapter(context)
        content_container.setAdapter(adapter)
        adapter.setAllData(leftColData(),topRowData(),cellData())
    }
    fun topRowData():List<Toptitle>{
        var topTitles = ArrayList<Toptitle>()
        var week = arrayOf("Senin","Selasa","Rabu","Kamis","Jum'at","Sabtu")

        for (item in week){
            var topTitle = Toptitle()
            topTitle.title = item
            topTitles.add(topTitle)
        }
        return topTitles
    }

    fun leftColData():List<LeftTitle>{
        var leftTitle = ArrayList<LeftTitle>()
        for (i in 1..7){
            var leftTitles = LeftTitle()
            leftTitles.title = i.toString() + "tes"
            leftTitle.add(leftTitles)
        }
        return leftTitle
    }

    fun cellData():List<List<cell>>{
        var cells = ArrayList<List<cell>>()

        for (i in 0..6){
            var cellList = ArrayList<cell>()
            cells.add(cellList)

            for(i in 0..6){
                var cell = cell()
                var random = Random()
                var number = random.nextInt(6)

                if(number == 1 ){
                    cell.title = "khidir"
                }else if(number == 2){
                    cell.title = "wira"
                }else if(number == 3){
                    cell.title = "sya'bani"
                }
                cellList.add(cell)
            }
        }
        return cells
    }
}