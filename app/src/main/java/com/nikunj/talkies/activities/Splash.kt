package com.nikunj.talkies.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.nikunj.talkies.R

class Splash : AppCompatActivity() {

    private val SplashTimeOut=3000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                val i = Intent(this, DashBoard::class.java)
                startActivity(i)
                finish()
            }, SplashTimeOut
        )
    }


}
