package com.example.krishmeet_moengage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignUp_activity : AppCompatActivity() {

    private lateinit var name:TextView
    private lateinit var email:TextView
    private lateinit var pwd:TextView
    private lateinit var sup_btn:Button

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        name = findViewById(R.id.name_txt_sup)
        email = findViewById(R.id.email_txt_sgup)
        pwd = findViewById(R.id.pwd_txt_sup)
        sup_btn = findViewById(R.id.sup_btn)

        sup_btn.setOnClickListener {
            if(name.text.toString().isEmpty()){
                Toast.makeText(this,"Please Fill valid fields",Toast.LENGTH_SHORT).show()
            }
            else if(email.text.toString().isEmpty()){
                Toast.makeText(this,"Please Fill valid fields",Toast.LENGTH_SHORT).show()
            }
            else if(pwd.text.toString().isEmpty()){
                Toast.makeText(this,"Please Fill valid fields",Toast.LENGTH_SHORT).show()
            }
            else{
                val name = name.text.toString()
                val email = email.text.toString()
                val password = pwd.text.toString()

                Signup_Auth(email,password)
            }

        }



    }

    private fun Signup_Auth(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")

                    val intent = Intent(this@SignUp_activity,MainActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
    companion object {
        private const val TAG = "EmailPassword"
    }
}