package com.example.pdf_genie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        val signInBtn=findViewById<Button>(R.id.signInBtn)
        signInBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        val signUpBtn=findViewById<Button>(R.id.signUpBtn)
        signUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}