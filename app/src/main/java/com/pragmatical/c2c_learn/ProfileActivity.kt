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
import com.pragmatical.c2c_learn.databinding.ActivityMainBinding
import com.pragmatical.c2c_learn.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=FirebaseAuth.getInstance()
        database= Firebase.database.reference
        val currentUser=auth.currentUser
        var userFromDb=User()
        database.child("users").child(currentUser?.uid.toString()).get().addOnSuccessListener {
            userFromDb= it.value as User
        }.addOnFailureListener{
            Toast.makeText(baseContext, "ERROR: " + binding.editTextTextEmailAddress.text,
                Toast.LENGTH_SHORT).show()
        }
        binding = ActivityProfileBinding.inflate(layoutInflater)
        binding.editTextTextEmailAddress.setText(userFromDb.email.toString())
        binding.editTextUserName.setText(userFromDb.username.toString())
        binding.editTextFullName.setText(userFromDb.fullName.toString())
        setContentView(binding.root)
        Toast.makeText(baseContext, "Logged In User: " + binding.editTextTextEmailAddress.text,
            Toast.LENGTH_SHORT).show()

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

    private fun writeNewUser(userId: String, userName: String, email: String) {
        val user = User(userName, email)
        database.child("users").child(userId).setValue(user)
    }
}