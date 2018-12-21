package com.example.zhack.share_ie

import android.content.DialogInterface
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.zhack.share_ie.berita.control_berita
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.appcompat.v7.alertDialogLayout

class MainActivity : AppCompatActivity() {

    var session:SessionManagment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        session = SessionManagment(applicationContext)
        session!!.checkLogin()
            initFragment(control_berita())
            berita.setColorFilter(R.color.colorPrimaryDark)
            berita.setOnClickListener {
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

    override fun onBackPressed() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Keluar")
        dialog.setMessage("Yakin Ingin Keluar ?")
        dialog.setPositiveButton("YA"){dialog, which->
            val intent = Intent(applicationContext,Login::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("EXIT",true)
            startActivity(intent)
            finishAffinity()
        }
        dialog.setNegativeButton("TIDAK"){dialog,which->
            dialog.cancel()
        }
        dialog.show()
    }

}
