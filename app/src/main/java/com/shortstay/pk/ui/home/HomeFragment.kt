package com.shortstay.pk.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.nostra13.universalimageloader.core.assist.QueueProcessingType
import com.nostra13.universalimageloader.core.download.BaseImageDownloader
import com.shortstay.pk.databinding.FragmentHomeBinding
import com.shortstay.pk.responseModels.nearbyHotels.Data
import com.shortstay.pk.ui.CommonFragment
import com.shortstay.pk.utils.CustomPagerTransformer
import com.shortstay.pk.utils.Utils
import java.lang.reflect.Field

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        init()
        return root
    }

    private lateinit var hotelObject: ArrayList<Data>
    private fun init() {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.callApi()
        hotelObject = ArrayList()
        homeViewModel.hotels.observe(viewLifecycleOwner, {
            hotelObject.addAll(it)
            fillViewPager()
        })
        homeViewModel.progress.observe(viewLifecycleOwner,{
            if (it)
                Utils.showProgress(requireContext())
            else
                Utils.hideProgress()
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                activity?.window?.statusBarColor = Color.TRANSPARENT
                activity?.window?.decorView?.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                activity?.window?.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                )
            }
        }
        dealStatusBar()
        initImageLoader()

    }

    var size = 0
    private val fragments: ArrayList<CommonFragment> = ArrayList()
    private fun fillViewPager() {
        _binding?.viewpager?.setPageTransformer(false, CustomPagerTransformer(requireActivity()))
        for (i in hotelObject) {
            fragments.add(CommonFragment())
        }
        _binding?.viewpager?.adapter =
            object : FragmentStatePagerAdapter(activity?.supportFragmentManager!!) {
                override fun getCount(): Int {
                    return hotelObject.size
                }

                override fun getItem(position: Int): Fragment {
                    val fragment: CommonFragment = fragments[position % 10]
                    fragment.bindData(hotelObject[position].hotel_images[position % hotelObject.size],hotelObject,position)
                    return fragment
                }

            }

        _binding?.viewpager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                updateIndicatorTv()
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        updateIndicatorTv()
    }


    private fun dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val statusBarHeight: Int = getStatusBarHeight()
            val lp: ViewGroup.LayoutParams? = _binding?.positionView?.layoutParams
            lp?.height = statusBarHeight
            _binding?.positionView?.layoutParams = lp
        }
    }

    @SuppressLint("PrivateApi")
    private fun getStatusBarHeight(): Int {
        val c: Class<*>?
        val obj: Object?
        val field: Field?
        var x = 0
        var statusBarHeight = 0
        try {
            c = Class.forName("com.android.internal.R\$dimen")
            obj = c.newInstance() as Object
            field = c.getField("status_bar_height")
            x = field[obj]?.toString()?.toInt()!!
            statusBarHeight = resources.getDimensionPixelSize(x)
        } catch (e1: Exception) {
            e1.printStackTrace()
        }
        return statusBarHeight
    }

    private fun initImageLoader() {
        val config = ImageLoaderConfiguration.Builder(
            activity
        )
            .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
            .threadPoolSize(3) // default
            .threadPriority(Thread.NORM_PRIORITY - 1) // default
            .tasksProcessingOrder(QueueProcessingType.FIFO) // default
            .denyCacheImageMultipleSizesInMemory()
            .memoryCache(LruMemoryCache(2 * 1024 * 1024))
            .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
            .discCacheSize(50 * 1024 * 1024)
            .discCacheFileCount(100)
            .discCacheFileNameGenerator(HashCodeFileNameGenerator()) // default
            .imageDownloader(BaseImageDownloader(activity)) // default
            .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
            .writeDebugLogs().build()

        val imageLoader = ImageLoader.getInstance()
        imageLoader.init(config)
    }

    private fun updateIndicatorTv() {
        val totalNum: Int? = _binding?.viewpager?.adapter?.count
        val currentItem: Int? = _binding?.viewpager?.currentItem?.plus(1)
        _binding?.countIndicator?.text =
            Html.fromHtml("<font color='#12edf0'>$currentItem</font>  /  $totalNum")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}