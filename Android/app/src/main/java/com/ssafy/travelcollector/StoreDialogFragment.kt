package com.ssafy.travelcollector


import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.ssafy.travelcollector.databinding.FragmentStoreDialogBinding



class StoreDialogFragment (private val context : Context): DialogFragment() {

    private lateinit var binding : FragmentStoreDialogBinding
    private val dlg = Dialog(context)

    fun show(content : String) {
        binding = FragmentStoreDialogBinding.inflate(LayoutInflater.from(context))

//        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(binding.root)

        val window = dlg.window
        val params = window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT  // 너비 최대로
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT // 높이는 내용에 맞게
        window?.attributes = params

        dlg.setCancelable(false)

        dlg.show()
    }

}