package com.example.suitmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class First_Screen : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var palindrome: EditText
    private lateinit var check_button: Button
    private lateinit var next_button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_screen)
        supportActionBar?.hide()

        name= findViewById(R.id.Name_ET)
        palindrome= findViewById(R.id.Palindrome_ET)
        check_button= findViewById(R.id.Check_btn)
        next_button= findViewById(R.id.Next_btn)

        check_button.setOnClickListener {
            var noSpace_palindrome= palindrome.text.toString().replace(" ","")
            var palindrome_rev= noSpace_palindrome.reversed()

            if(noSpace_palindrome == palindrome_rev){
                Toast.makeText(this, "isPalindrome",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"not palindrome", Toast.LENGTH_SHORT).show()
            }
        }
        next_button.setOnClickListener {
            val intent = Intent(this, Second_Screen::class.java)
            var username= name.text.toString()
            intent.putExtra("Name",username)
            startActivity(intent)
        }

    }
}