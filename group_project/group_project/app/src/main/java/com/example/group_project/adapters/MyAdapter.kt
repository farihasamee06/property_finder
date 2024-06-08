package com.example.group_project.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.group_project.Propertydescription
import com.example.group_project.R
import com.example.group_project.model.Renters

class MyAdapter(var yourListData:List<Renters>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            // Retrieve the clicked product ID
//            val productId = yourListData[adapterPosition].id
            val address= yourListData[adapterPosition].address
            val rent=yourListData[adapterPosition].rent
            val amenties=yourListData[adapterPosition].noOfBedrooms
            val img=yourListData[adapterPosition].image
            val description=yourListData[adapterPosition].Description
            // Create an intent to navigate to the Product Details Screen
            val intent = Intent(itemView.context, Propertydescription::class.java).apply {
                putExtra("ADDRESS",address)
                putExtra("RENT",rent)
                putExtra("AMENTIES",amenties)
                putExtra("IMAGE",img)
                putExtra("DESCRIPTION",description)
            }
//                .apply {
//                putExtra("PRODUCT_ID", productId)
//            }
            itemView.context.startActivity(intent)
        }
    }

    // tell the function which layout file to use for each row of the recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }


    // how many items are in the list
    override fun getItemCount(): Int {
        return yourListData.size
    }


    // In a single row, what data goes in the <TextView>?
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currItem: Renters = yourListData.get(position)

//        val tvRow1= holder.itemView.findViewById<TextView>(R.id.txt1)
        val tvRow2= holder.itemView.findViewById<TextView>(R.id.txt2)
        val tvRow3= holder.itemView.findViewById<TextView>(R.id.txt3)
        val tvRow4= holder.itemView.findViewById<TextView>(R.id.txt4)
//        val tvRow5= holder.itemView.findViewById<TextView>(R.id.txt5)
//        val tvRow6= holder.itemView.findViewById<TextView>(R.id.txt6)
        val tvRow7= holder.itemView.findViewById<TextView>(R.id.txt7)

//        tvRow1.text="Name: ${currItem.Name}"
        tvRow2.text="Address: ${currItem.address}"
        tvRow3.text="Type: ${currItem.type}"
        tvRow4.text="Amenties: ${currItem.noOfBedrooms}"
//        tvRow5.text="Bathrooms provided: ${currItem.Bathroom}"
//        tvRow6.text="Bedrooms provided: ${currItem.BedRoom}"
        tvRow7.text="Rent: ${currItem.rent}"

        val iv = holder.itemView.findViewById<ImageView>(R.id.img)
        Log.d("TESTING", "What is the image: ${currItem.image}")
        Glide.with(holder.itemView.context)
            .load(currItem.image)
            .into(iv)
        }
    }

