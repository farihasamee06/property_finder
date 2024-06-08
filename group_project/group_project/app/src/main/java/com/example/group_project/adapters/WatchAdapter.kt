package com.example.group_project.adapters

import com.example.group_project.R



import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.group_project.model.Renters

class WatchAdapter (var yourListData:List<Renters>) : RecyclerView.Adapter<WatchAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder (itemView) {
    }


    // tell the function which layout file to use for each row of the recycler view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.watchscreen_layout, parent, false)
        return MyViewHolder(view)
    }


    // how many items are in the list
    override fun getItemCount(): Int {
        return yourListData.size
    }


    // In a single row, what data goes in the <TextView>?
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currItem: Student = yourListData.get(position)
val currItem: Renters = yourListData.get(position)

        val tvRow1=holder.itemView.findViewById<TextView>(R.id.tvaddress)
        val tvRow2=holder.itemView.findViewById<TextView>(R.id.tvrent)
        val tvRow3=holder.itemView.findViewById<TextView>(R.id.tvdescription)

        tvRow1.text="Address:- "+currItem.address
        tvRow2.text="Rent:- "+currItem.rent
    tvRow3.text="Description:- "+currItem.Description

        /*Glide.with(context)
            .load(currItem.image)
            .into(imageView)*/


        }
    }

