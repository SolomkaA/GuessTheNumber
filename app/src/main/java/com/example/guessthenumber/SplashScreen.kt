package com.example.guessthenumber

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent,
            ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        finish()
    }
}