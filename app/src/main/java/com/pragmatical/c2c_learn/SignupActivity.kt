package com.pragmatical.c2c_learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.pragmatical.c2c_learn.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            // Check if email and password are not empty
            if (isValidEmail(email) && isPasswordStrong(password)) {
                // Create a new user with Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Authentication Successful",
                                Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign up fails, display a message to the user
                            Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Show appropriate error messages
                if (!isValidEmail(email)) {
                    Toast.makeText(this, "Invalid Email Format", Toast.LENGTH_SHORT).show()
                }
                if (!isPasswordStrong(password)) {
                    Toast.makeText(this, "Password is not strong enough", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        // Add regular expression for email validation
        return email.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
    }
    private fun isPasswordStrong(password: String): Boolean {
        // Add conditions for strong password (e.g., length, contains numbers/special characters)
        return password.length > 6 // Add more conditions as needed
    }
}