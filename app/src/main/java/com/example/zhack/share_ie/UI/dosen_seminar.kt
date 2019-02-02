package com.example.zhack.share_ie.UI

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Transition
import android.transition.TransitionInflater
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.example.zhack.share_ie.API.ApiClient
import com.example.zhack.share_ie.R
import com.example.zhack.share_ie.SessionManagment
import com.example.zhack.share_ie.model.dosen_seminar_model
import com.example.zhack.share_ie.model.status_dosen_seminar
import kotlinx.android.synthetic.main.activity_dosen_seminar.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.list_seminar_category.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class dosen_seminar: AppCompatActivity(), adapter_seminar_dosen.Listener {
    override fun onItemClick(view: View, position: Int) {

    }

    private var posisi:Int =0
    private var sessionManagment:SessionManagment?=null
    private var adapter:adapter_seminar_dosen?=null
    private var mAndrodiList:ArrayList<dosen_seminar_model>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManagment = SessionManagment(applicationContext)
        setContentView(R.layout.activity_dosen_seminar)

        setSupportActionBar(toolbar_all)
        supportActionBar?.title = "Dosen Seminar"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation()
        }

        fab.setOnClickListener{
            animateRevealClose()
        }

        var nil = intent.getIntExtra("nilai",0)
        var client = ApiClient.create()
        var detail = client.getDosenSeminar(sessionManagment!!.UserId(), sessionManagment!!.UserToken(), nil)
        Log.d("dosen_seminar_dalam", nil.toString())
        detail.enqueue(object: Callback<status_dosen_seminar>{
            override fun onFailure(call: Call<status_dosen_seminar>, t: Throwable) {
                Log.d("dosen_seminar_dalam", "error")
            }

            override fun onResponse(call: Call<status_dosen_seminar>, response: Response<status_dosen_seminar>) {
                if (response.isSuccessful) {
                    Log.d("dosen_seminar_dalam", "berhasil")
                    var body = response.body()!!.detail
                    var list:List<dosen_seminar_model> = body
                    mAndrodiList = ArrayList(list)
                    adapter = adapter_seminar_dosen(mAndrodiList!!,this@dosen_seminar)
                    initRecyclerView()
                    rv_dosen_smr.adapter = adapter
                }else{
                    Log.d("dosen_seminar_dalam", "gagal")
                }
            }

        })

        Log.d("dosen_seminar", intent.getIntExtra("nilai",0).toString())
    }

    private fun ShowEnterAnimation() {
        val transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition)
        window.sharedElementEnterTransition = transition

        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                cvAdd.visibility = View.GONE
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {

            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionResume(transition: Transition) {

            }


        })
    }

    fun animateRevealShow() {
        val mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.width / 2, 0, (fab.width / 2).toFloat(), cvAdd.height.toFloat())
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
            }

            override fun onAnimationStart(animation: Animator) {
                cvAdd.visibility = View.VISIBLE
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }

    fun animateRevealClose() {
        val mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.width / 2, 0, cvAdd.height.toFloat(), (fab.width / 2).toFloat())
        mAnimator.duration = 500
        mAnimator.interpolator = AccelerateInterpolator()
        mAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                cvAdd.visibility = View.INVISIBLE
                super.onAnimationEnd(animation)
                fab.setImageResource(R.drawable.ic_signup)
                super@dosen_seminar.onBackPressed()
            }

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
            }
        })
        mAnimator.start()
    }

    override fun onBackPressed() {
        animateRevealClose()
    }

    public fun getNilai(nilai:Int){
        posisi = nilai
    }

    private fun initRecyclerView() {

        rv_dosen_smr.setHasFixedSize(true)
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        rv_dosen_smr.layoutManager = layoutManager
    }
}