package com.zzulfiqor.amikomcentercrudkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        val delayedHandler = Handler()
        delayedHandler.postDelayed({

            val intent = Intent(this, CrudZuhair::class.java)
            startActivity(intent)

        }, 1500)

    }
}