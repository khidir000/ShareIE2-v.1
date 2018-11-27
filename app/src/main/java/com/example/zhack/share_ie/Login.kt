package com.example.zhack.share_ie

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.login_username.*

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_username)

        login.setOnClickListener {
            val inte = Intent(this@Login
                    ,MainActivity::class.java)
            startActivity(inte)
        }
    }
}
