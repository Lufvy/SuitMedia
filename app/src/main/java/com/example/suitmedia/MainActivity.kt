package com.example.suitmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent =  Intent(this, First_Screen :: class.java)
        supportActionBar?.hide()
        startActivity(intent)
        finish()
    }
}