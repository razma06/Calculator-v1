package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in_activity.*
import java.util.Calendar.getInstance

class sign_in_activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_activity)
        init()
    }

    private fun init(){
        SignIner.setOnClickListener(){
            OnLogIn()
        }
        auth = Firebase.auth
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this, Calculator::class.java))
        }
    }

    private fun OnLogIn(){
        val emailAdd = signEmail.text.toString()
        val passLog = signPassword.text.toString()
        if(emailAdd.isNotEmpty() && passLog.isNotEmpty()) {
            SignIner.isClickable = false
            signProgress.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(emailAdd, passLog)
                .addOnCompleteListener(this) { task ->
                    SignIner.isClickable = true
                    signProgress.visibility = View.GONE
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        startActivity(Intent(this, Calculator  :: class.java))
                        
                        this.finish()

                    } else {
                        val report = task.exception
                        Toast.makeText(
                            baseContext, "$report",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }
        }else{
            Toast.makeText(this, "fill all fields", Toast.LENGTH_SHORT).show()
        }
    }



}