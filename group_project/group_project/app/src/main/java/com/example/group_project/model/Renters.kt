package com.example.group_project.model

import com.google.firebase.firestore.DocumentId

data class Renters (
    @DocumentId
    var id:String = "",
//    var Address:String="",
//    var Amenities:String="",
//    var Bathroom:Int=0,
//    var BedRoom:Int=0,
//    var Name:String="",
//    var Type:String="",
//    var Rent:String="",
   var Description:String="",
//    var imgURL:String=""
    var address:String="",
    var image:String="",
    @JvmField
    var isAvaiable:Boolean=true,
    var noOfBedrooms:Int=0,
    var rent:Int=0,
    var latitude:Double=0.0,
    var longitude:Double=0.0,
    var type:String=""

)