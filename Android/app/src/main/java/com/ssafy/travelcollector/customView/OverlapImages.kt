package com.ssafy.travelcollector.customView

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginLeft
import androidx.core.view.size
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.databinding.OverlapImagesBinding
import com.ssafy.travelcollector.dto.TravelWithHeritageList
import de.hdodenhof.circleimageview.CircleImageView
import okhttp3.internal.wait

private const val TAG = "OverlapImages"
class OverlapImages constructor(context: Context, attrs: AttributeSet) :  ConstraintLayout(context, attrs){

    private val binding: OverlapImagesBinding by lazy{
        OverlapImagesBinding.bind(
            LayoutInflater.from(context).inflate(R.layout.overlap_images, this, false)
        )
    }

    @SuppressLint("ResourceType")
    fun setImages(imgUrlList: ArrayList<String>){
        val imgArray = arrayListOf<CircleImageView>()
        val cnt = imgUrlList.size

        if(cnt == 0){
            binding.firstImg.visibility = VISIBLE
            binding.firstImg.borderWidth = 0
            binding.firstImg.setImageResource(Color.TRANSPARENT)
        }
        if(cnt>0) imgArray.add(binding.firstImg)
        if(cnt>1) imgArray.add(binding.secondImg)
        if(cnt>2) imgArray.add(binding.thirdImg)

        for((index,img) in imgArray.withIndex()) {
            img.visibility = VISIBLE
            Glide.with(context)
                .load(imgUrlList[index])
                .fallback(R.drawable.ic_launcher_foreground)
                .into(img)
        }
        
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