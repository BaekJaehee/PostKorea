package com.ssafy.travelcollector.fragment.others

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.StoreAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentStoreBinding
import com.ssafy.travelcollector.databinding.StoreDialogBinding
import com.ssafy.travelcollector.dto.Product
import kotlinx.coroutines.launch

class StoreFragment : BaseFragment<FragmentStoreBinding>(
    FragmentStoreBinding::bind,
    R.layout.fragment_store
) {

    private val storeAdapter: StoreAdapter by lazy{
        StoreAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()

    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        mainActivityViewModel.setPageTitle("상점")
        lifecycleScope.launch{
            storeViewModel.loadProductList()
            accountViewModel.user.collect{
                binding.storeBtnPts.text = "보유 pts : ${it.point}"
            }
        }
    }

    private fun initAdapter(){
        lifecycleScope.launch{
            storeViewModel.productList.collect{
                storeAdapter.submitList(it)
            }
        }
        storeAdapter.eventListener = object : StoreAdapter.EventListener {
            override fun onClick(position: Int) {
                openPurchaseDialog(storeAdapter.currentList[position])
            }

        }
        binding.storeRvProduct.adapter = storeAdapter
    }

    @SuppressLint("SetTextI18n")
    private fun openPurchaseDialog(product: Product) {
        val binding = StoreDialogBinding.inflate(layoutInflater)
        val customDialog = Dialog(mainActivity)
        customDialog.setContentView(binding.root)

        binding.productName.text = product.name
        val curPts = accountViewModel.user.value.point
        val afterPts = curPts - product.point
        binding.beforePts.text = "$curPts p"
        binding.afterPts.text = "$afterPts p"

        val window = customDialog.window
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT  // 너비 최대로
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT // 높이는 내용에 맞게
        window?.attributes = params

        customDialog.setCancelable(false)

        binding.btnConfirm.setOnClickListener {
            lifecycleScope.launch {
                storeViewModel.purchaseProduct(product.id){
                    accountViewModel.getInfo(it)
                    storeViewModel.loadProductList()
                }
            }
            customDialog.dismiss()
        }

        binding.btnCancel.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }
}