package com.ssafy.travelcollector.customView

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.databinding.ProductDetailDialogBinding
import com.ssafy.travelcollector.dto.Product

class ProductDetailDialog(context: Context, private val product: Product): Dialog(context) {

    private lateinit var binding: ProductDetailDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductDetailDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding){
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Glide.with(context)
            .load(product.image)
            .into(productDetailImg)
        productDetailTitle.text = product.name
        productDetailDesc.text= product.desc
        productDetailConfirm.setOnClickListener { dismiss() }
    }

}