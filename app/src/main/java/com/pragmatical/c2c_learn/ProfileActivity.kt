package com.pragmatical.c2c_learn


import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.pragmatical.c2c_learn.databinding.ActivityProfileBinding
import com.pragmatical.c2c_learn.models.User
import java.util.UUID


class ProfileActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var profileImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Get firebase auth instance and store id for currently logged in user
        auth=FirebaseAuth.getInstance()
        val currentUser=auth.currentUser
        val currentUserId=currentUser?.uid.toString()
        //get realtime database reference
        database= Firebase.database.reference
        var userFromDb: User
        database.child("users").child(currentUserId).get().addOnSuccessListener {
            userFromDb= it.getValue(User::class.java)!!
            //Populate profile activity fields
            //username and full name populated from db
            if(userFromDb.userName !="null")
                binding.editTextUserName.setText(userFromDb.userName)
            if(userFromDb.fullName !="null")
                binding.editTextFullName.setText(userFromDb.fullName.toString())
        }.addOnFailureListener{
            Toast.makeText(baseContext, "ERROR: " + it.message!!,
                Toast.LENGTH_SHORT).show()
        }
        //email is populated from the currently logged in user
        binding.editTextTextEmailAddress.setText(currentUser?.email)
        //get the profile image for the current user
        displayProfileImage(currentUserId)

        binding.saveButton.setOnClickListener{
            database= Firebase.database.reference
            val userName=binding.editTextUserName.text.toString()
            val fullName=binding.editTextFullName.text.toString()
            database.child("users").child(currentUserId).child("userName").setValue(userName)
            database.child("users").child(currentUserId).child("fullName").setValue(fullName)
            saveProfileImage(currentUserId)
            Toast.makeText(baseContext, "Profile updated successfully!",
                Toast.LENGTH_SHORT).show()
        }

        binding.buttonSetImage.setOnClickListener{
            launchGallery()
        }

    }

    private fun displayProfileImage(currentUserId: String){
        val storage = Firebase.storage
        val storageRef = storage.reference
        //get image that is stored with the name of the userid
        val profileImageRef = storageRef.child("images/$currentUserId.jpeg")
        profileImageRef.getBytes(5048576)
            .addOnSuccessListener { bytes ->
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                binding.imageView.setImageBitmap(bmp)
            }.addOnFailureListener {

            }
    }

    private fun saveProfileImage(currentUserId: String){
        val storage = Firebase.storage
        val storageReference = storage.reference
        val ref: StorageReference =
            storageReference.child("images/$currentUserId.jpeg")
        ref.putFile(profileImageUri)
            .addOnSuccessListener {
                Toast.makeText(this@ProfileActivity, "Uploaded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@ProfileActivity, "Failed " + e.message, Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnProgressListener { taskSnapshot ->
                val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                    .totalByteCount
            }
    }
    private fun launchGallery() {
        previewRequest.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
    }

    private val previewRequest =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
                uri ->
                if (uri != null) {
                    binding.imageView.setImageURI(uri)
                    profileImageUri=uri
                } else {
                    Log.d("PhotoPicker", "No media selected")
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