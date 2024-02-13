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

    private val _ownList = MutableStateFlow(arrayListOf<Product>())
    val ownList = _ownList.asStateFlow()

    fun loadProductList(){
        viewModelScope.launch {
            var own: Set<Int>
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.STORE_SERVICE.getProducts()
            }
            val res2 = withContext(Dispatchers.IO){
                RetrofitUtil.STORE_SERVICE.getCollection(AccountViewModel.ACCESS_TOKEN)
            }
            if(res.code()/100 == 2 && res2.code()/100 == 2){
                val newList = res.body()!!
                own = res2.body()!!.filter{it.date!=null}.map{it.id}.toSet()
                for(product in newList){
                    if(own.contains(product.id))
                        product.isPurchasable = false
                }
                setProductList(ArrayList(newList))
            }
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
//                _ownProductList.update { ArrayList(res.body()!!.filter{it.date!=null}) }
//                _notOwnProductList.update { ArrayList(res.body()!!.filter{it.date==null})}
                _ownList.update { ArrayList(res.body()!!) }
            }
        }
    }



}