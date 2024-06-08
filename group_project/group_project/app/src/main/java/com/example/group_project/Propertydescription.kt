package com.example.group_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.group_project.adapters.MyAdapter
import com.example.group_project.databinding.ActivityPropertydescriptionBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Propertydescription : AppCompatActivity() {
    lateinit var binding: ActivityPropertydescriptionBinding

    // create a Firebase auth variable
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertydescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnfirstlogin.isVisible = false

        // initialize Firebase auth
        auth = Firebase.auth

        if (intent == null) {
            // error
            val snackbar =
                Snackbar.make(binding.root, "ERROR: No intent found", Snackbar.LENGTH_LONG)
            snackbar.show()
        } else {

//            val productId = intent.getIntExtra("PRODUCT_ID",-1)
            val rentalAddress = intent.getStringExtra("ADDRESS",)
            val rentalrent = intent.getIntExtra("RENT", -1)
            val rentalamenties = intent.getIntExtra("AMENTIES", -1)
            val rentalimg = intent.getStringExtra("IMAGE",)
            val rentaldescription=intent.getStringExtra("DESCRIPTION",)

            rentalimg?.let { imageUrl ->
                Glide.with(this)
                    .load(imageUrl)
                    .into(binding.adaptimg)
            }

            binding.tv1.text = "Address:- " + rentalAddress.toString()
            binding.tv2.text = "Rent:- $" + rentalrent.toString()
            binding.tv3.text = "Ameneties:- " + rentalamenties.toString()
            binding.tv4.text="Description:- "+rentaldescription.toString()
        }

        binding.btnWatch.setOnClickListener {
            if(auth.currentUser!= null)
            {
                val intent:Intent= Intent(this@Propertydescription,WatchScreen::class.java)
                startActivity(intent)
            }
            else
            {
                val snackbar=Snackbar.make(binding.root,"Please login first",Snackbar.LENGTH_LONG)
                snackbar.show()

                binding.btnfirstlogin.isVisible=true

                binding.btnfirstlogin.setOnClickListener {
                    val intent= Intent(this@Propertydescription,LoginScreen::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}