package com.example.suitmedia

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class Second_Screen : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var username: TextView
    private lateinit var selected_user: TextView
    private lateinit var choose_button: Button
    private lateinit var back_button: ImageButton
    private val REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)
        supportActionBar?.hide()

        val user= intent.getStringExtra("Name")
        sharedViewModel= ViewModelProvider(this).get(SharedViewModel::class.java)

        selected_user= findViewById(R.id.selected_user_sc)
        username= findViewById(R.id.username_sc)
        choose_button= findViewById(R.id.choose_user_btn_sc)
        back_button= findViewById(R.id.back_button_sc)


        back_button.setOnClickListener {
            onBackPressed()
        }


        sharedViewModel.selectedUserName.observe(this, {fullname->
            if(fullname != null){
                selected_user.text=fullname
            }
        })

        username.text=user
        choose_button.setOnClickListener {
            val intent = Intent(this, Third_Screen::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra("SELECTED_USER")?.let { selectedUser ->
                sharedViewModel.selectUser(selectedUser)
            }
        }
    }

}