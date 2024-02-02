package com.ssafy.travelcollector.customView

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import androidx.core.view.size
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.databinding.OverlapImagesBinding
import okhttp3.internal.wait

private const val TAG = "OverlapImages"
class OverlapImages constructor(context: Context, attrs: AttributeSet) :  ConstraintLayout(context, attrs){

    private val binding: OverlapImagesBinding by lazy{
        OverlapImagesBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.overlap_images, this, false)
        )
    }

    fun setImages(){
        val imgArray = arrayListOf(binding.firstImg, binding.secondImg, binding.thirdImg)
        Glide.with(context)
            .load(R.drawable.cultural_asset_image_temp)
            .fallback(R.drawable.ic_launcher_foreground)
            .into(binding.firstImg)
    }

    init{
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OverlapImages)
        val size = typedArray.getInt(R.styleable.OverlapImages_size, 70)
        binding.root.scaleX = size / 70f
        binding.root.scaleY = size / 70f

        typedArray.recycle()
        addView(binding.root)
    }

}