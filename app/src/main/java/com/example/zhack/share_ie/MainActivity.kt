package com.example.zhack.share_ie

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment(control_berita())
        berita.setColorFilter(R.color.colorPrimaryDark)
        berita.setOnClickListener{
            initFragment(control_berita())
            berita.setColorFilter(R.color.colorPrimaryDark)
            seminar.clearColorFilter()
            jadwal.clearColorFilter()
            laporan.clearColorFilter()
        }
    }

    private fun initFragment(classFragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, classFragment)
        transaction.commit()
    }

}
