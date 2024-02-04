package com.ssafy.travelcollector.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeritageViewModel: ViewModel() {

    private val _curHeritage = MutableStateFlow(Heritage())
    val curHeritage = _curHeritage.asStateFlow()

    fun setCurHeritage(heritage: Heritage){
        _curHeritage.update { heritage }
    }

    fun loadHeritageDetail(id: Int){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RetrofitUtil.HERITAGE_SERVICE.getHeritageDetail(id).body()
            }
            setCurHeritage(result!!)
        }
    }

    //눈에 보이는 여행 목록. db에 저장되지 않는 임시 리스트.
    //테마 내의 목록이나 문화재 검색 결과가 될 수 있다
    private var _curHeritageList = MutableStateFlow(arrayListOf<Heritage>())
    val curHeritageList = _curHeritageList.asStateFlow()
    fun setCurHeritageList(list: ArrayList<Heritage>){
        _curHeritageList.update { list }
    }


    fun loadHeritageList(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RetrofitUtil.HERITAGE_SERVICE.getHeritageList()
            }
            result.body()?.let { ArrayList(it) }?.let { setCurHeritageList(it) }
        }
    }

}