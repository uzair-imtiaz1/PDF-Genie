package com.example.pdf_genie

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

class SignInActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        val textView = findViewById<TextView>(R.id.txt)
        val spannableString = SpannableString(textView.text)

        val startIndex = textView.text.indexOf("Sign up")
        val endIndex = startIndex + "Sign up".length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
            }
        }
        spannableString.setSpan(clickableSpan, startIndex, endIndex, 0)
        textView.text = spannableString
        textView.movementMethod = android.text.method.LinkMovementMethod.getInstance()


        editTextEmail = findViewById(R.id.l_mail)
        editTextPassword = findViewById(R.id.l_pass)
        btnSignIn = findViewById(R.id.l_btn)

        btnSignIn.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authenticateUser(email, password)
                startActivity(Intent(this, ChatActivity::class.java))
            } else {
                Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun authenticateUser(email: String, password: String) {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this
            ) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("Login", user!!.email + " successfully login.")
                    Toast.makeText(this@SignInActivity, "Authentication successful.", Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("Login", "failure", task.exception)
                    Toast.makeText(this@SignInActivity, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}