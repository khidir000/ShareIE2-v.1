package com.example.zhack.share_ie

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.zhack.share_ie.R.attr.logo
import kotlinx.android.synthetic.main.splash_screen.*

class SplashScreen : AppCompatActivity() {

    private var delay :Handler? = null
    private val splas: Long = 2500


    internal val mRunnable:Runnable = Runnable {
        if(!isFinishing){
            val intent = Intent(this@SplashScreen, Login::class.java)
            startActivity(intent)
            this.finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.splash_screen)

        delay = Handler()
        delay!!.postDelayed(mRunnable, splas)
        animasi()
    }

    override fun onBackPressed() {
        //aksi ketika tombol kembali ditekan
    }

    override fun onDestroy() {
        super.onDestroy()
    }
//    public override fun onPause() {
//        super.onPause()
//        this@SplashScreen.finish()
//        System.exit(1)
//    }
//
//    public override fun onStop() {
//        super.onStop()
//        this@SplashScreen.finish()
//        System.exit(1)
//    }
//
    private fun animasi(){
        val animasi = AnimationUtils.loadAnimation(this,R.anim.buram)
        logo.startAnimation(animasi)
    }
    companion object {
        private val jeda = 3000
    }
}