package com.pragmatical.c2c_learn

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.Firebase
import com.google.firebase.database.database

import com.pragmatical.c2c_learn.databinding.ActivityContentUploadBinding
import java.util.UUID

class ContentUploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContentUploadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityContentUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener{
            val postText = binding.editTextTextMultiLine.text.toString() // Capture text
            val postView = createCardView(postText)
            binding.linearLayoutPosts.addView(postView) // Add to LinearLayout
            //Save post to Firebase
            val postId = UUID.randomUUID().toString() // Generate unique ID for the post
            val databaseRef = Firebase.database.reference
            databaseRef.child("posts").child(postId).setValue(postText)
            binding.editTextTextMultiLine.setText("") // Clear input field for the next post
        }

    }

    private fun createCardView(message:String):CardView{
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
        date.text = "Date"
        date.textSize = 16f
        date.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC)
        date.setTextColor(Color.DKGRAY)

        val cardContent = TextView(this)
        cardContent.text = message
        cardContent.textSize = 24f
        cardContent.setTextColor(Color.DKGRAY)
        cardContent.setTypeface(Typeface.SANS_SERIF,Typeface.NORMAL)

        val name = TextView(this)
        name.text = "Author"
        name.textSize = 16f
        name.setTypeface(Typeface.MONOSPACE, Typeface.ITALIC)
        name.setTextColor(Color.DKGRAY)

        cardLinearLayout.addView(date)
        cardLinearLayout.addView(cardContent)
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