package com.ssafy.travelcollector

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ssafy.travelcollector.adapter.StoreAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentStoreBinding
import com.ssafy.travelcollector.databinding.StoreDialogBinding
import com.ssafy.travelcollector.dto.Product
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


private const val TAG = "StoreFragment"
class StoreFragment : BaseFragment<FragmentStoreBinding>(FragmentStoreBinding::bind, R.layout.fragment_store) {

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
        storeAdapter.eventListener = object : StoreAdapter.EventListener{
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

        // 확인 버튼 누르면 할 일 작성
        binding.btnConfirm.setOnClickListener {
            Toast.makeText(mainActivity, "Purchase completed", Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                storeViewModel.purchaseProduct(product.id){
                    accountViewModel.getInfo(it)
                }
            }
            customDialog.dismiss()
        }

        // 취소 버튼 누르면 할 일 작성
        binding.btnCancel.setOnClickListener {
            Toast.makeText(mainActivity, "Purchase canceled", Toast.LENGTH_SHORT).show()
            customDialog.dismiss()
        }
        customDialog.show()
    }
}