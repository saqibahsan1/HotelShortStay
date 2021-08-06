package com.shortstay.pk.ui.hotel

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.shortstay.pk.R
import com.shortstay.pk.responseModels.SliderItem
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.*

class HotelImagesAdapter(private val context: Context) :
    SliderViewAdapter<HotelImagesAdapter.SliderAdapterVH?>() {
    private val mSliderItems: List<SliderItem> = ArrayList<SliderItem>()
    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        val sliderItem: SliderItem = mSliderItems[position]
        viewHolder?.textViewDescription?.text = sliderItem.description
        viewHolder?.textViewDescription?.textSize = 16f
        viewHolder?.textViewDescription?.setTextColor(Color.WHITE)
        Glide.with(viewHolder?.itemViews!!)
            .load(sliderItem.imageUrl)
            .fitCenter()
            .into(viewHolder.imageViewBackground)
        viewHolder.itemViews!!.setOnClickListener {
            Toast.makeText(
                context,
                "This is item in position $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getCount(): Int {
        //slider view count could be dynamic size
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.iv_auto_image_slider)
        var textViewDescription: TextView = itemView.findViewById(R.id.tv_auto_image_slider)
        var itemViews: View? = itemView
    }
}