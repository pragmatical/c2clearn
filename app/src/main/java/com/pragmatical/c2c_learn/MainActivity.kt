package com.pragmatical.c2c_learn

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.pragmatical.c2c_learn.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.title.text="This is the title"
        binding.subhead.text="This is the subhead"
        binding.body.text="This is text for the body of the card"
        binding.cardView1.setOnClickListener{
        // Obtain an instance of the Firebase Realtime Database
        // Obtain an instance of the Firebase Realtime Database
        val database = FirebaseDatabase.getInstance()
        // Reference a specific location in the database, here we choose 'message'
        // Reference a specific location in the database, here we choose 'message'
        val myRef = database.getReference("message")
            // Write a message to the database
        myRef.setValue("Hello, World!")

        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}