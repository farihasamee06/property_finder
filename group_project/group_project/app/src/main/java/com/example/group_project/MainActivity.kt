package com.example.group_project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.CreationExtras
//import androidx.lifecycle.viewmodel.CreationExtras.Empty.map
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.group_project.adapters.MyAdapter
import com.example.group_project.databinding.ActivityMainBinding
import com.example.group_project.model.Renters
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityMainBinding
    val db = Firebase.firestore
    lateinit var myAdapter: MyAdapter
    private lateinit var auth: FirebaseAuth

    private lateinit var map: GoogleMap
//    private val locationPermissionCode = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setSupportActionBar(binding.myToolbar)

        auth= Firebase.auth
        binding.btnSearch.setOnClickListener {
            var searchKeyword=binding.etSearchText.text.toString()
            var rent=searchKeyword.toInt()
            searchByPrice(rent)
        }

    }

private fun searchByPrice(price:Int)
{
        db.collection("properties")
            .whereLessThanOrEqualTo("rent", price)
            .get()
            .addOnSuccessListener {
                result->
                var datalist:MutableList<Renters> = mutableListOf()
                for (document in result)
                {
                    val renter:Renters = document.toObject(Renters::class.java)
                    datalist.add(renter)
                }
                myAdapter = MyAdapter(datalist)

                // recyclerview configuration
                binding.rv.adapter = myAdapter
                binding.rv.layoutManager = LinearLayoutManager(this)
                binding.rv.addItemDecoration(
                    DividerItemDecoration(
                        this,
                        LinearLayoutManager.VERTICAL
                    )
                )
                myAdapter.notifyDataSetChanged()
                Log.d("TESTING", datalist.toString())

            }

}
    override fun onMapReady(googleMap: GoogleMap) {

                    map = googleMap

                    // Enable zoom controls on the map
                    map.uiSettings.isZoomControlsEnabled = true
                    // Example: Add a marker in Sydney, Australia, and move the camera

        // Get the properties collection from Firestore
        val propertiesRef = Firebase.firestore.collection("properties")

        // Retrieve data from Firestore
        propertiesRef.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    // Get latitude and longitude from the document
                    val latitude = document.getDouble("latitude")
                    val longitude = document.getDouble("longitude")
                    val address = document.getString(("address"))
                    // Add marker to the map if latitude and longitude are not null
                    if (latitude != null && longitude != null) {
                        val propertyLocation = LatLng(latitude, longitude)
                        map.addMarker(MarkerOptions().position(propertyLocation).title("Property Marker:${address}"))
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
//                    val sydney = LatLng(-34.0, 151.0)
//                    map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//                    map.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.mi_option_1->
            {
                val mapFragment = supportFragmentManager.findFragmentById(R.id.map)
                mapFragment?.view?.visibility = View.VISIBLE
                true
            }
            R.id.mi_option_2 -> {

                val mapFragment = supportFragmentManager.findFragmentById(R.id.map)
                mapFragment?.view?.visibility = View.GONE
                // create a temporary list of renters
                var dataToDisplay: MutableList<Renters> = mutableListOf()
                db.collection("properties")
                    .get()
                    .addOnSuccessListener { result: QuerySnapshot ->

                        // for each document in the collection,
                        for (document: QueryDocumentSnapshot in result) {
                            // convert the document to a Renters object
                            val renter: Renters = document.toObject(Renters::class.java)

                            // then add the document to the temporary list
                            dataToDisplay.add(renter)
                        }
//update the recycler view

                        myAdapter = MyAdapter(dataToDisplay)
                        // recyclerview configuration
                        binding.rv.adapter = myAdapter
                        binding.rv.layoutManager = LinearLayoutManager(this)
                        binding.rv.addItemDecoration(
                            DividerItemDecoration(
                                this,
                                LinearLayoutManager.VERTICAL
                            )
                        )
                        myAdapter.notifyDataSetChanged()
                        Log.d("TESTING", dataToDisplay.toString())

                    }
                    .addOnFailureListener { exception ->
                        Log.d("TESTING", "Error: retrieving document", exception)
                    }

                true
            }
            R.id.mi_option_4 -> {
                val intent= Intent(this@MainActivity,LoginScreen::class.java)
                startActivity(intent)
                true
            }
R.id.mi_option_5->
{
    if (auth.currentUser == null) {
       val snackbar = Snackbar.make(binding.root,"CANNOT LOGOUT AS NO USER LOGGED IN ",Snackbar.LENGTH_LONG)
        snackbar.show()

    }
    else
    {
        Firebase.auth.signOut()
        val snackbar = Snackbar.make(binding.root,"USER SUCCESSFULLY LOGGED OUT",Snackbar.LENGTH_LONG)
        snackbar.show()
    }
    true
}
            else -> super.onOptionsItemSelected(item)
        }

        }

}

