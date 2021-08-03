package com.shortstay.pk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.nostra13.universalimageloader.core.ImageLoader
import com.shortstay.pk.R
import com.shortstay.pk.databinding.FragmentCommonBinding
import com.shortstay.pk.responseModels.nearbyHotels.Data
import com.shortstay.pk.utils.DragLayout
import java.text.FieldPosition

class CommonFragment : Fragment(), DragLayout.GotoDetailListener {

    private var commonBinding: FragmentCommonBinding? = null
    private var imageView: ImageView? = null
    private var name: View? = null
    private var address2: View? = null
    private var address3: View? = null
    private var ratingBar: RatingBar? = null
    private var address4: View? = null
    private var address5: View? = null
    private val bindings get() = commonBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        commonBinding = FragmentCommonBinding.inflate(inflater, container, false)
        val root: View = bindings.root
        init(root)
        return root
    }

    private fun init(root: View) {
        val dragLayout = root.findViewById(R.id.drag_layout) as (DragLayout)
        imageView = dragLayout.findViewById(R.id.image) as ImageView
        ImageLoader.getInstance().displayImage(imageUrl, imageView)
        name = dragLayout.findViewById(R.id.hotelName)
        address2 = dragLayout.findViewById(R.id.address2)
        address3 = dragLayout.findViewById(R.id.hotelAddress)
        address4 = dragLayout.findViewById(R.id.hotelDescription)
        address5 = dragLayout.findViewById(R.id.details)
        ratingBar = dragLayout.findViewById(R.id.rating) as RatingBar
        commonBinding?.hotelName?.text = hotels[position!!].hotel_name
        commonBinding?.availableRooms?.text = hotels[position!!].available_rooms.toString()
        commonBinding?.hotelAddress?.text = hotels[position!!].address
        commonBinding?.totalRooms?.text = hotels[position!!].total_rooms.toString()
        commonBinding?.hotelDescription?.text = hotels[position!!].hotel_descriptions
        commonBinding?.rating?.rating = hotels[position!!].rating.toFloat()

    }

    private var imageUrl: String? = null
    private var position: Int? = null
    private lateinit var hotels: ArrayList<Data>
    override fun gotoDetail() {
        TODO("Not yet implemented")
    }

    fun bindData(imageUrl: String?, mHotels: List<Data>, position: Int?) {
        this.imageUrl = imageUrl
        this.position = position
        hotels = ArrayList()
        hotels.clear()
        hotels.addAll(mHotels)
    }

    override fun onDestroy() {
        super.onDestroy()
        commonBinding = null
    }

}