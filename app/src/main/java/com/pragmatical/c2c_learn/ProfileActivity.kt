package com.pragmatical.c2c_learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.pragmatical.c2c_learn.databinding.ActivityProfileBinding
import com.pragmatical.c2c_learn.models.User

class ProfileActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=FirebaseAuth.getInstance()
        database= Firebase.database.reference
        val currentUser=auth.currentUser
        val currentUserId=currentUser?.uid.toString()
        val userFromDb= User()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding.editTextTextEmailAddress.setText(currentUser?.email)
        database.child("users").child(currentUser?.uid.toString()).get().addOnSuccessListener {
            userFromDb.userName=it?.child("userName")?.value.toString()
            userFromDb.fullName=it?.child("fullName")?.value.toString()
            userFromDb.profilePic=it?.child("profilePic")?.value.toString()
            if(userFromDb.userName!="null")
                binding.editTextUserName.setText(userFromDb.userName)
            if(userFromDb.fullName!="null")
                binding.editTextFullName.setText(userFromDb.fullName.toString())
            setContentView(binding.root)
            Toast.makeText(baseContext, "Logged In User: " + binding.editTextTextEmailAddress.text,
                Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(baseContext, "ERROR: " + it.message!!,
                Toast.LENGTH_SHORT).show()
        }

        binding.saveButton.setOnClickListener{
            database= Firebase.database.reference
            val userName=binding.editTextUserName.text.toString()
            val fullName=binding.editTextFullName.text.toString()
            database.child("users").child(currentUserId).child("userName").setValue(userName)
            database.child("users").child(currentUserId).child("fullName").setValue(fullName)
            Toast.makeText(baseContext, "Profile updated successfully!",
                Toast.LENGTH_SHORT).show()
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}