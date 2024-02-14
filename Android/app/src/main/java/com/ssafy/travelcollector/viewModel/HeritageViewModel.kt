package com.ssafy.travelcollector.viewModel

import android.util.Log
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

private const val TAG = "HeritageViewModel"
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
    private val _curHeritageList = MutableStateFlow(arrayListOf<Heritage>())
    val curHeritageList = _curHeritageList.asStateFlow()
    private fun setCurHeritageList(list: ArrayList<Heritage>){
        _curHeritageList.update { list }
    }

    private val _listPageHeritageList = MutableStateFlow(arrayListOf<Heritage>())
    val listPageHeritageList = _listPageHeritageList.asStateFlow()

    private fun setListHeritageList(list: List<Heritage>){
        _listPageHeritageList.update { ArrayList(list) }
    }

    fun loadHeritageList(){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RetrofitUtil.HERITAGE_SERVICE.getHeritageList()
            }
            if(result.code()/100 == 2){
                setListHeritageList(ArrayList(result.body()!!))
            }
        }
    }

    fun searchHeritageList(region1: String?, region2: String?, era: String?, category: String?){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RetrofitUtil.HERITAGE_SERVICE.searchHeritage(region1, region2, era, category)
            }
            if(result.code()/100 == 2){
                result.body()?.let { ArrayList(it) }?.let { setListHeritageList(it) }
            }else{
                Log.d(TAG, "searchHeritageList: $result")
            }
        }
    }

    fun searchHeritageListRandom(region1: String?, region2: String?, era: String?, category: String?){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RetrofitUtil.HERITAGE_SERVICE.searchHeritageRandom(region1, region2, era, category)
            }
            if(result.code()/100 == 2){
                result.body()?.let { ArrayList(it) }?.let { setCurHeritageList(it) }
            }
        }
    }

    fun searchHeritageListForGame(category: String?, limit: Int?){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RetrofitUtil.HERITAGE_SERVICE.searchHeritageForGame(category, limit)
            }
            result.body()?.let { ArrayList(it) }?.let { setCurHeritageList(it) }
        }
    }

    private var _heritageListByName = MutableStateFlow(arrayListOf<Heritage>())
    val heritageListByName = _heritageListByName.asStateFlow()

    fun setHeritageListByName(list: ArrayList<Heritage>){
        _heritageListByName.update { list }
    }
    fun searchHeritageByName(name: String){
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RetrofitUtil.HERITAGE_SERVICE.searchHeritageByName(name)
            }
            result.body()?.let { ArrayList(it) }?.let {
                setHeritageListByName(it)
                setCurHeritageList(it)
                setListHeritageList(it)
            }
        }
    }

    fun editPoints(points : Int, callback: ()->Unit){
        viewModelScope.launch{
            val res = RetrofitUtil.HERITAGE_SERVICE.editPoints(
                AccountViewModel.ACCESS_TOKEN, points
            )
            if(res.code()/100 == 2){
                callback()
            }

        }
    }



}