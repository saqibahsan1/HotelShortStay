package com.shortstay.pk.ui.hotel

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.shortstay.pk.R
import com.shortstay.pk.databinding.HotelDetailsBinding
import com.shortstay.pk.responseModels.SliderItem
import com.shortstay.pk.utils.DataProvider
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.hotel_details.view.*
import java.util.*


class HotelDetails : AppCompatActivity() {

    private var sliderView: SliderView? = null
    private var adapter: HotelImagesAdapter? = null
    private var adapterAmenities: HotelAmenitiesAdapter? = null
    private lateinit var binding: HotelDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HotelDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        adapter = HotelImagesAdapter(this)
        adapterAmenities = HotelAmenitiesAdapter(this,DataProvider.data.amenities)
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            true
        )
        binding.recyclerView.adapter = adapterAmenities
        binding.ratingBar.rating = DataProvider.data.rating.toFloat()
        binding.description.text = DataProvider.data.hotel_descriptions
        binding.totalRooms.text = getString(R.string.total_rooms)+ "" +DataProvider.data.total_rooms.toString()
        binding.availableRooms.text = getString(R.string.available_rooms)+ "" +DataProvider.data.available_rooms.toString()
        sliderView = binding.imageSlider
        sliderView?.setSliderAdapter(adapter!!)
        sliderView?.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)
        sliderView?.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION)
        sliderView?.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        sliderView?.indicatorSelectedColor = Color.WHITE
        sliderView?.indicatorUnselectedColor = Color.GRAY
        sliderView?.scrollTimeInSec = 3
        sliderView?.isAutoCycle = true
        sliderView?.startAutoCycle()

        ViewCompat.setTransitionName(
            binding.imageSlider,
            IMAGE_TRANSITION_NAME
        )
        ViewCompat.setTransitionName(
            binding.availableRooms,
            ADDRESS1_TRANSITION_NAME
        )
        ViewCompat.setTransitionName(
            binding.description,
            ADDRESS2_TRANSITION_NAME
        )
        ViewCompat.setTransitionName(
            binding.cvSliderMainActivity,
            ADDRESS3_TRANSITION_NAME
        )
        ViewCompat.setTransitionName(
            binding.buttonBook,
            ADDRESS4_TRANSITION_NAME
        )
        ViewCompat.setTransitionName(
            binding.totalRooms,
            ADDRESS5_TRANSITION_NAME
        )
        ViewCompat.setTransitionName(
            binding.ratingBar,
            RATINGBAR_TRANSITION_NAME
        )
        val sliderItemList: MutableList<SliderItem> = ArrayList<SliderItem>()
        for (i in DataProvider.data.hotel_images.indices) {
            val sliderItem = SliderItem()
            sliderItem.description = DataProvider.data.address
            sliderItem.imageUrl = DataProvider.data.hotel_images[i]
            sliderItemList.add(sliderItem)
        }
        adapter?.hotelSliderItems(sliderItemList)
    }

    companion object {
        const val EXTRA_IMAGE_URL = "detailImageUrl"
        const val IMAGE_TRANSITION_NAME = "transitionImage"
        const val ADDRESS1_TRANSITION_NAME = "address1"
        const val ADDRESS2_TRANSITION_NAME = "address2"
        const val ADDRESS3_TRANSITION_NAME = "address3"
        const val ADDRESS4_TRANSITION_NAME = "address4"
        const val ADDRESS5_TRANSITION_NAME = "address5"
        const val RATINGBAR_TRANSITION_NAME = "ratingBar"
    }
}