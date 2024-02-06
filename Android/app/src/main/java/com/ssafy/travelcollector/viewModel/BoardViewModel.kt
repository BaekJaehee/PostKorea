package com.ssafy.travelcollector.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.travelcollector.dto.Board
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

private const val TAG = "BoardViewModel"
class BoardViewModel: ViewModel() {

    fun postBoard(title: String, content: String, images: ArrayList<MultipartBody.Part>){
        viewModelScope.launch {
            val res = RetrofitUtil.BOARD_SERVICE.uploadProfileImage(
                id = withContext(Dispatchers.IO){
                    RetrofitUtil.BOARD_SERVICE.postBoard(
                        AccountViewModel.ACCESS_TOKEN, Board(title, content)
                    ).body()!!.id
                },
                images = images.toList()
            )
            Log.d(TAG, "postBoard: $res" +
                    "\n ${res.body()} " +
                    "\n ${res.code()}")
        }
    }


}