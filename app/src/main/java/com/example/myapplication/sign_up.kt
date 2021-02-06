package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class sign_up : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init(){
        signUpper.setOnClickListener(){
            OnSignUp()
        }
        auth = Firebase.auth

    }
    private fun OnSignUp(){
        val UpEmail = EmailField.text.toString()
        val UpPassword = PasswordField.text.toString()
        val UpRepeatPass = RepeatPasswordField.text.toString()
        if (UpEmail.isNotEmpty() && UpPassword.isNotEmpty() && UpPassword == UpRepeatPass) {
            if (UpEmail.isValidEmail()){
                progress.visibility = View.VISIBLE
                signUpper.isClickable = false
                auth.createUserWithEmailAndPassword(UpEmail, UpPassword)
                    .addOnCompleteListener(this) { task ->
                        signUpper.isClickable = true
                        progress.visibility = View.GONE
                        if (task.isSuccessful) {
                            Toast.makeText(this, "SignUp is Success!", Toast.LENGTH_LONG).show()
                            val user = auth.currentUser

                        } else {
                            val problem = task.exception
                            Toast.makeText(baseContext, "$problem",
                                Toast.LENGTH_LONG).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "email format is incorrect", Toast.LENGTH_SHORT).show()
            }

        } else if(UpEmail.isEmpty() || UpPassword.isEmpty() || UpRepeatPass.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Passwords doesn't match", Toast.LENGTH_SHORT).show()
        }

    }
    fun String.isValidEmail() =
        this.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}