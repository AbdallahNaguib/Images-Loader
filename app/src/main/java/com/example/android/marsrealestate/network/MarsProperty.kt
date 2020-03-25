
package com.example.android.marsrealestate.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/*
 this the object that is returned by the API
 note : it's attributes should match the names in the JSON
 */

@Parcelize
data class MarsProperty(val id:String,
                   val price:Double,
                   val type:String,
                   @Json(name="img_src") val imgSrcUrl:String):Parcelable{
    val isRental
    get() = type=="rent"
}
