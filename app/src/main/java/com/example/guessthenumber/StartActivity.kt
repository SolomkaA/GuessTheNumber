package com.example.guessthenumber

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)

        overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left)
        var pStatus = 0

        val progressBar: ProgressBar = findViewById<View>(R.id.progressBar) as ProgressBar


        Thread {
            while (pStatus <= 1000) {
                Handler(Looper.getMainLooper()).post {
                    progressBar.progress = pStatus
                }
                try {
                    Thread.sleep(3)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                pStatus++
            }
        }.start()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            finish()
        }, 4100)



    }
}