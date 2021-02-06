package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_calculator.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception
import kotlin.system.exitProcess

class Calculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        init()
    }

    private fun init(){
        Button00.setOnClickListener{textType("00")}
        Button0.setOnClickListener{textType("0")}
        Button1.setOnClickListener{textType("1")}
        Button2.setOnClickListener{textType("2")}
        Button3.setOnClickListener{textType("3")}
        Button4.setOnClickListener{textType("4")}
        Button5.setOnClickListener{textType("5")}
        Button6.setOnClickListener{textType("6")}
        Button7.setOnClickListener{textType("7")}
        Button8.setOnClickListener{textType( "8")}
        Button9.setOnClickListener{textType( "8")}
        ButtonDot.setOnClickListener{textType(".")}
        ButtonEqual.setOnClickListener(){
            calculate()
        }
        ButtonBack.setOnClickListener(){
            clear(inputText.text.toString())
        }
        ButtonBack.setOnLongClickListener{
            inputText.text = ""
            outputText.text = ""
            return@setOnLongClickListener true
        }



        ButtonPlus.setOnClickListener(){textType( "+")}
        ButtonMinus.setOnClickListener(){textType("-")}
        ButtonMultiply.setOnClickListener(){textType( "*")}
        ButtonDivide.setOnClickListener(){textType( "/")}




        LogOut.setOnClickListener(){
            startActivity(Intent(this, MainActivity::class.java))
            Firebase.auth.signOut()
            this.finish()
        }

        BackgroundButton.setOnClickListener(){
            val popup = PopupMenu(this, BackgroundButton)
            popup.inflate(R.menu.theme_menu)
            popup.setOnMenuItemClickListener {
                when(it.title){
                    "Blue" -> {
                        mainScreen.setBackgroundResource(R.drawable.blue_light_gradient_background)
                    }
                    "Orange" -> {
                        mainScreen.setBackgroundResource(R.drawable.orange_gradient_background)
                    }
                    "Dark Blue" -> {
                        mainScreen.setBackgroundResource(R.drawable.blue_dark_gradient_background)
                    }
                }
                true
            }
            popup.show()
        }


    }
    override fun onBackPressed(){
        super.onBackPressed()
        this.finish()
    }

    private fun textType(string:String){
        inputText.append(string)
        outputText.text = ""

    }

    private fun calculate(){

        try{
            val input = ExpressionBuilder(inputText.text.toString()).build()
            val output = input.evaluate()
            val longOutput = output.toLong()
            if(output == longOutput.toDouble()){
                outputText.text = longOutput.toString()
            }else{
                outputText.text = output.toString()
            }

        }catch (e:Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT ).show()
        }
    }

    private fun clear(str:String) {
        if (str != "") {
            val txt = str.substring(0, str.length - 1)
            inputText.text = txt
            outputText.text = ""
        }

    }
}