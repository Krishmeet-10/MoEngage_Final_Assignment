package com.example.krishmeet_moengage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class login_activity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    private lateinit var signin_btn:Button
    private lateinit var signup_btn:Button
    private lateinit var email_txt : TextView
    private lateinit var pw_txt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        signin_btn = findViewById(R.id.signin_btn)
        signup_btn = findViewById(R.id.signup_btn)
        email_txt = findViewById(R.id.emailtxt)
        pw_txt = findViewById(R.id.passtxt)

        signin_btn.setOnClickListener{
            if(email_txt.text.toString().isEmpty()){
                Toast.makeText(this,"Please Fill valid fields",Toast.LENGTH_SHORT).show()
            }
            else if(pw_txt.text.toString().isEmpty()){
                Toast.makeText(this,"Please Fill valid fields",Toast.LENGTH_SHORT).show()
            }
            else{
                val email:String = email_txt.text.toString()
                val pw:String = pw_txt.text.toString()
                login_Auth(email,pw)
            }

        }

        signup_btn.setOnClickListener {
            intent = Intent(this,SignUp_activity::class.java)
            startActivity(intent)
        }
    }

    fun login_Auth(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val intent = Intent(this@login_activity,MainActivity::class.java)
                    startActivity(intent)


                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}