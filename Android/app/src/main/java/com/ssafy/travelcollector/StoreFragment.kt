package com.ssafy.travelcollector

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentStoreBinding
//import com.ssafy.travelcollector.databinding.StoreDialogBinding


class StoreFragment : BaseFragment<FragmentStoreBinding>(FragmentStoreBinding::bind, R.layout.fragment_store) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.storeBtnPts.setOnClickListener {
//            customDialogFunction()
        }
    }

//    private fun customDialogFunction() {
//        val binding = StoreDialogBinding.inflate(layoutInflater)
//        val customDialog = Dialog(mainActivity)
//        customDialog.setContentView(binding.root)
//
//        val window = customDialog.window
//        val params = window?.attributes
//        params?.width = WindowManager.LayoutParams.MATCH_PARENT  // 너비 최대로
//        params?.height = WindowManager.LayoutParams.WRAP_CONTENT // 높이는 내용에 맞게
//        window?.attributes = params
//
//        customDialog.setCancelable(false)
//
//        // 확인 버튼 누르면 할 일 작성
//        binding.btnConfirm.setOnClickListener {
//            Toast.makeText(mainActivity, "Purchase completed", Toast.LENGTH_SHORT).show()
//            customDialog.dismiss()
//        }
//
//        // 취소 버튼 누르면 할 일 작성
//        binding.btnCancel.setOnClickListener {
//            Toast.makeText(mainActivity, "Purchase canceled", Toast.LENGTH_SHORT).show()
//            customDialog.dismiss()
//        }
//        customDialog.show()
//    }
}