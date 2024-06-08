package com.example.group_project

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.group_project.adapters.MyAdapter
import com.example.group_project.databinding.ActivityMainBinding
import com.example.group_project.databinding.ActivityWatchScreenBinding
import com.example.group_project.model.Renters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WatchScreen : AppCompatActivity() {
    lateinit var binding: ActivityWatchScreenBinding
    val db = Firebase.firestore
    lateinit var myAdapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWatchScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun loadData() {
        db.collection("properties")
            .get()
            .addOnSuccessListener { documents ->
                val dataList: MutableList<Renters> = mutableListOf()

                for (document in documents) {
                    val renter: Renters = document.toObject(Renters::class.java)
                    dataList.add(renter)
                }

                myAdapter = MyAdapter(dataList)
                binding.rv.adapter = myAdapter
                binding.rv.layoutManager = LinearLayoutManager(this)
                binding.rv.addItemDecoration(
                    DividerItemDecoration(
                        this,
                        LinearLayoutManager.VERTICAL
                    )
                )
                myAdapter.notifyDataSetChanged()
                Log.d("TESTING", dataList.toString())
            }
            .addOnFailureListener { exception ->
                Log.d("TESTING", "Error getting documents: ", exception)
            }
    }
}