package com.example.pdf_genie

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnSignIn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        val textView = findViewById<TextView>(R.id.s_text)
        val spannableString = SpannableString(textView.text)

        val startIndex = textView.text.indexOf("Sign in")
        val endIndex = startIndex + "Sign in".length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
            }
        }
        spannableString.setSpan(clickableSpan, startIndex, endIndex, 0)

        textView.text = spannableString
        textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()

        editTextEmail=findViewById(R.id.s_mail)
        editTextPassword=findViewById(R.id.s_pass)
        btnSignIn=findViewById(R.id.s_btn)

        btnSignIn.setOnClickListener{
            registerUser(editTextEmail.text.toString(),editTextPassword.text.toString())
            startActivity(Intent(this, ChatActivity::class.java))
        }
    }
    private fun registerUser(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    if (user != null) {
                        Log.d("Registration", user.email + " successfully Registered.")
                    }
                    Toast.makeText(this@SignUpActivity, "Registration successful.", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("Registration", "failure", task.exception)
                    Toast.makeText(this@SignUpActivity, "Registration failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}