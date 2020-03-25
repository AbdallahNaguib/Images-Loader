
package com.example.android.marsrealestate

import android.opengl.Visibility
import android.util.Log
import android.view.View.*
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.android.marsrealestate.network.MarsProperty
import com.example.android.marsrealestate.overview.MarsApiStatus
import com.example.android.marsrealestate.overview.PhotoGridAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data:List<MarsProperty>?){
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("loadingStatus")
fun loadingStatus(imgView: ImageView , status: MarsApiStatus?){
    status?.let {
        when(status){
            MarsApiStatus.DONE->imgView.visibility = GONE
            MarsApiStatus.ERROR-> {
                imgView.visibility = VISIBLE
                imgView.setImageResource(R.drawable.ic_connection_error)
            }
            MarsApiStatus.LOADING -> {
                imgView.visibility = VISIBLE
                imgView.setImageResource(R.drawable.loading_img)
            }
        }
    }
}

@BindingAdapter("imageUrl")
fun bindImg(imgView:ImageView , imgUrl:String?){
    imgUrl?.let{
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions().placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}