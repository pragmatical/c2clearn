package com.pragmatical.c2c_learn

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

import com.pragmatical.c2c_learn.databinding.ActivityContentUploadBinding
import com.pragmatical.c2c_learn.models.Post
import com.pragmatical.c2c_learn.models.User
import java.util.Date
import java.util.UUID

class ContentUploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentUploadBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbRef=Firebase.database.reference

        auth=FirebaseAuth.getInstance()
        val currentUser=auth.currentUser
        val currentUserId=currentUser?.uid.toString()

        binding=ActivityContentUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Retrieve posts from DB
        dbRef.child("posts").orderByChild("createdDate")
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val postFromDB=postSnapshot.getValue(Post::class.java)!!
                    val postView=createCardView(dbRef,postFromDB)
                    binding.linearLayoutPosts.addView(postView,0)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })

        binding.buttonSave.setOnClickListener{
            val postId = UUID.randomUUID().toString() // Generate unique ID for the post
            val postText = binding.editTextTextMultiLine.text.toString() // Capture text
            val postProfilePic="https://cataas.com/cat"
            // Add to LinearLayout
            //Save post to Firebase
            val post= Post(content=postText, createdBy = currentUserId, createdDate = System.currentTimeMillis(), imageUrl = postProfilePic)
            val postView = createCardView(dbRef,post)
            binding.linearLayoutPosts.addView(postView,0)
            val databaseRef = Firebase.database.reference
            databaseRef.child("posts").child(postId).setValue(post)
            binding.editTextTextMultiLine.setText("") // Clear input field for the next post
        }

    }



    private fun createCardView(dbRef: DatabaseReference, post:Post):CardView{
        val cardLinearLayout = LinearLayout(this)

        cardLinearLayout.orientation = LinearLayout.VERTICAL
        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(16,16,16,16)

        val cardView = CardView(this)
        cardView.radius = 15f
        cardView.setCardBackgroundColor(Color.parseColor("#ffffff"))
        cardView.setContentPadding(36,36,36,36)
        cardView.layoutParams = params
        cardView.cardElevation = 30f

        val date = TextView(this)
        date.text = Date(post.createdDate!!).toString()
        date.textSize = 16f
        date.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC)
        date.setTextColor(Color.DKGRAY)

        val cardContent = TextView(this)
        cardContent.text = post.content
        cardContent.textSize = 24f
        cardContent.setTextColor(Color.DKGRAY)
        cardContent.setTypeface(Typeface.SANS_SERIF,Typeface.NORMAL)


        val name = TextView(this)
        name.textSize = 16f
        name.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC)
        name.setTextColor(Color.DKGRAY)
        val profilePicView= ImageView(this)
        if(post.imageUrl!="null")
            Glide.with(this)
                .load(post.imageUrl) // URL from the post data
                .into(profilePicView) // ImageView in your post item layout
        val currentUser=auth.currentUser
        if(currentUser!=null)
            dbRef.child("users").child(post.createdBy!!).get().addOnSuccessListener {
                val userFromDb= it.getValue(User::class.java)!!
                //Populate profile activity fields
                //username and full name populated from db
                if(userFromDb.userName !="null")
                    name.text=userFromDb.userName.toString()
            }.addOnFailureListener{
                Toast.makeText(baseContext, "ERROR: " + it.message!!,
                    Toast.LENGTH_SHORT).show()
            }


        cardLinearLayout.addView(date)
        cardLinearLayout.addView(cardContent)
        cardLinearLayout.addView(profilePicView)
        cardLinearLayout.addView(name)
        cardView.addView(cardLinearLayout)

        return cardView
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
            R.id.menu_profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}