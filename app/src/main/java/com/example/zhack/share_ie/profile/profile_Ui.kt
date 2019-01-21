package com.example.zhack.share_ie.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.UI.create_status
import com.example.zhack.share_ie.UI.jadwal_profile
import com.example.zhack.share_ie.UI.profile_berita
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile.*

class profile_Ui : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener{

    var sessionManagment:SessionManagment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = inflater.inflate(R.layout.profile,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sessionManagment = SessionManagment(context)
        sessionManagment!!.checkLogin()
        super.onViewCreated(view, savedInstanceState)
        var image = sessionManagment!!.UserFoto()
        Picasso.get().load(image).into(image_profile)
        nama_profile.text = sessionManagment!!.UserName()
        nim_profile.text = sessionManagment!!.UserUsername()
        email_profile.text = sessionManagment!!.UserEmail()
        Log.d("profile = ",image+" "+sessionManagment!!.UserName())
        initFragment(profile_berita())
        btm_nav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.jadwal_profile->{initFragment(jadwal_profile())}
                R.id.berita_profile->{initFragment(profile_berita())}
                else -> {
                    initFragment(profile_berita())
                }
            }

        }
//        val fieldFragment = jadwal_profile()
//        initFragment(fieldFragment)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment = jadwal_profile()

        when(item.itemId){
            R.id.berita_profile-> {initFragment(fragment)
                return true}
            R.id.jadwal_profile-> initFragment(fragment)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initFragment(classFragment: Fragment): Boolean {

        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_profile, classFragment)
        transaction.commit()
        return true
    }

}