package com.ssafy.travelcollector.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "AccountViewModel"
class AccountViewModel: ViewModel(){

    private val _user = MutableStateFlow(User())
    val user = _user.asStateFlow()

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()
    var loginResponseCode: Int = 0

    fun login(id: String, pwd: String){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                RetrofitUtil.USER_SERVICE.login(
                    User(memberEmail = id, memberPwd = pwd)
                )
            }
            loginResponseCode = response.code()
            _accessToken.update {
                response.body()?.get("access_token").toString()
            }
        }
    }

    fun getInfo(token: String){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                RetrofitUtil.USER_SERVICE.getUserInfo("Bearer $token")
            }
            _user.update {
                it.copy(
                    memberEmail = response.body()?.get("user_email").toString(),
                    userNickname = response.body()?.get("user_nickname").toString(),
                )
            }
        }
    }

    private val _userInfoToSignUp = MutableStateFlow(User())
    private val userInfoToSignUP = _userInfoToSignUp.asStateFlow()
    fun passUserInfoToSignUp(user:User){
        _userInfoToSignUp.update{user}

    }
    fun getUserInfoToSignUp(): User{
        return  userInfoToSignUP.value
    }
}