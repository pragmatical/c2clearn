package com.pragmatical.c2c_learn

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
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
            R.id.menu_create_post -> {
                startActivity(Intent(this, ContentUploadActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}