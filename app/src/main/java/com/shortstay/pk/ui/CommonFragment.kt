package com.shortstay.pk.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import com.nostra13.universalimageloader.core.ImageLoader
import com.shortstay.pk.R
import com.shortstay.pk.databinding.FragmentCommonBinding
import com.shortstay.pk.utils.DragLayout

class CommonFragment : Fragment(), DragLayout.GotoDetailListener {

    private var commonBinding: FragmentCommonBinding? = null
    private var imageView: ImageView? = null
    private var address1: View? = null
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
        commonBinding = FragmentCommonBinding.inflate(inflater,container,false)
        val root : View = bindings.root
        init(root)
        return root
    }

    private fun init(root: View){
        val dragLayout = root.findViewById(R.id.drag_layout) as (DragLayout)
        imageView = dragLayout.findViewById(R.id.image) as ImageView
        ImageLoader.getInstance().displayImage(imageUrl, imageView)
        address1 = dragLayout.findViewById(R.id.address1)
        address2 = dragLayout.findViewById(R.id.address2)
        address3 = dragLayout.findViewById(R.id.address3)
        address4 = dragLayout.findViewById(R.id.address4)
        address5 = dragLayout.findViewById(R.id.address5)
        ratingBar = dragLayout.findViewById(R.id.rating) as RatingBar
    }

    private var imageUrl: String? = null
    override fun gotoDetail() {
        TODO("Not yet implemented")
    }
    fun bindData(imageUrl: String?) {
        this.imageUrl = imageUrl
    }

    override fun onDestroy() {
        super.onDestroy()
        commonBinding = null
    }

}