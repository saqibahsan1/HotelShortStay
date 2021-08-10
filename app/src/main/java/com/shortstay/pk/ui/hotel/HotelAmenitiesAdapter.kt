package com.shortstay.pk.ui.hotel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shortstay.pk.R

class HotelAmenitiesAdapter(var context: Context, private val imageList: List<String>) :
    RecyclerView.Adapter<HotelAmenitiesAdapter.CustomViewHolder?>() {

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewDescription: TextView? = itemView.findViewById(R.id.amenityTxt)
        var imageAmenity: ImageView? = itemView.findViewById(R.id.amenityImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.amenities, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        when (imageList[position]) {
            "wifi" -> {
                holder.imageAmenity?.setImageResource(R.drawable.ic_baseline_wifi_24)
                holder.textViewDescription?.text = imageList[position]
            }
            "ac" -> {
                holder.imageAmenity?.setImageResource(R.drawable.ac)
                holder.textViewDescription?.text = imageList[position]
            }
            "clean bath" -> {
                holder.imageAmenity?.setImageResource(R.drawable.ic_baseline_bathtub_24)
                holder.textViewDescription?.text = imageList[position]
            }
            "breakfast" -> {
                holder.imageAmenity?.setImageResource(R.drawable.ic_baseline_free_breakfast_24)
                holder.textViewDescription?.text = imageList[position]
            }
            "secure location" -> {
                holder.imageAmenity?.setImageResource(R.drawable.ic_baseline_security_24)
                holder.textViewDescription?.text = imageList[position]
            }


        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}