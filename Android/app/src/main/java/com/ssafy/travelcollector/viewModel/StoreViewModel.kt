package com.ssafy.travelcollector.viewModel

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

class StoreViewModel : ViewModel() {

    private val _productList = MutableStateFlow(arrayListOf(Product()))
    val productList = _productList.asStateFlow()

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

}