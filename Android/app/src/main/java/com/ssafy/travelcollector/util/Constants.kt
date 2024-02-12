package com.ssafy.travelcollector.util

import com.ssafy.travelcollector.dto.HeritageImage
import com.ssafy.travelcollector.R

object Constants {
    fun getImage() : ArrayList<HeritageImage> {
        val heritageImageList = ArrayList<HeritageImage>()

        val heritageImage1 = HeritageImage(
            1, "부석사", R.drawable.buseoksa
        )
        heritageImageList.add(heritageImage1)

        val heritageImage2 = HeritageImage(
            2, "통도사", R.drawable.tongdosa
        )
        heritageImageList.add(heritageImage2)

        val heritageImage3 = HeritageImage(
            3, "해인사", R.drawable.haeinsa
        )
        heritageImageList.add(heritageImage3)

        val heritageImage4 = HeritageImage(
            1, "부석사", R.drawable.buseoksa2
        )
        heritageImageList.add(heritageImage4)

        val heritageImage5 = HeritageImage(
            4, "불국사", R.drawable.bulguksa
        )
        heritageImageList.add(heritageImage5)

        val heritageImage6 = HeritageImage(
            5, "월정사", R.drawable.woljeongsa
        )
        heritageImageList.add(heritageImage6)

        val heritageImage7 = HeritageImage(
            1, "부석사", R.drawable.buseoksa3
        )
        heritageImageList.add(heritageImage7)

        val heritageImage8 = HeritageImage(
            6, "송광사", R.drawable.songgwangsa
        )
        heritageImageList.add(heritageImage8)

        val heritageImage9 = HeritageImage(
            2, "통도사", R.drawable.tongdosa2
        )
        heritageImageList.add(heritageImage9)

        val heritageImage10 = HeritageImage(
            3, "해인사", R.drawable.haeinsa2
        )
        heritageImageList.add(heritageImage10)

        return heritageImageList
    }
}