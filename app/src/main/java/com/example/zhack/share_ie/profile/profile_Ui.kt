package com.example.zhack.share_ie.profile

import android.app.Fragment
import android.app.FragmentManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import com.example.zhack.share_ie.R

class profile_Ui : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?) = inflater.inflate(R.layout.profile,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    private fun initFragment(classFragment: Fragment){
        val transaction = activity.fragmentManager.beginTransaction()
        transaction.replace(R.id.frame, classFragment)
        transaction.commit()
    }
}