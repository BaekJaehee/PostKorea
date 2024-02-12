package com.ssafy.travelcollector.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.travelcollector.dto.Product
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "StoreViewModel"
class StoreViewModel : ViewModel() {

    private val _productList = MutableStateFlow(arrayListOf<Product>())
    val productList = _productList.asStateFlow()

    private val _ownProductList = MutableStateFlow(arrayListOf<Product>())
    val ownProductList = _ownProductList.asStateFlow()

    private val _notOwnProductList = MutableStateFlow(arrayListOf<Product>())
    val notOwnProductList = _notOwnProductList.asStateFlow()

    fun loadProductList(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.STORE_SERVICE.getProducts()
            }
            setProductList(ArrayList(res.body()!!))
        }
    }

    fun setProductList(list: ArrayList<Product>){
        _productList.update { list }
    }

    fun purchaseProduct(id: Int, callback:(String)->Unit){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.STORE_SERVICE.purchaseProduct(
                    AccountViewModel.ACCESS_TOKEN,id
                )
            }
            if(res.code()/100==2){ callback.invoke(AccountViewModel.ACCESS_TOKEN) }
        }
    }

    fun loadCollection(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.STORE_SERVICE.getCollection(AccountViewModel.ACCESS_TOKEN)
            }
            if(res.code()/100 == 2){
                _ownProductList.update { ArrayList(res.body()!!.filter{it.date!=null}) }
                _notOwnProductList.update { ArrayList(res.body()!!.filter{it.date==null}) }
            }
        }
    }

}