package com.example.pdf_genie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val btn : Button =findViewById(R.id.continue_button)

        btn.setOnClickListener{
            startActivity(Intent(this, AuthenticationActivity::class.java))
        }
    }
}