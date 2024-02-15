package com.ssafy.travelcollector.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.travelcollector.dto.RequestPassword
import com.ssafy.travelcollector.dto.RequestUserInfo
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.util.RetrofitUtil
import com.ssafy.travelcollector.util.UriPartConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "AccountViewModel"
class AccountViewModel: ViewModel(){

    private val _user = MutableStateFlow(User(memberEmail = DEFAULT_EMAIL))
    val user = _user.asStateFlow()

    private val _accessToken = MutableStateFlow("")
    val accessToken = _accessToken.asStateFlow()
    var loginResponseCode: Int = 0

    companion object{
        var ACCESS_TOKEN: String = ""
        const val DEFAULT_EMAIL: String = "x@x"
    }

    fun updateToken(token: String){
        _accessToken.update { "Bearer $token" }
        ACCESS_TOKEN = "Bearer $token"
    }

    fun login(id: String, pwd: String){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                RetrofitUtil.USER_SERVICE.login(
                    User(memberEmail = id, memberPwd = pwd)
                )
            }
            loginResponseCode = response.code()
            if(response.code()/100 == 2){
                val token = response.body()?.get("access_token").toString()
                updateToken(token)
            }

        }
    }

    fun logout(){
        _user.update { User(memberEmail = DEFAULT_EMAIL) }
    }

    fun getInfo(token: String){
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO){
                RetrofitUtil.USER_SERVICE.getUserInfo(token)
            }
            if(response.code()/100 == 2){
                _user.update {
                    response.body()!!
                }
            }

        }
    }

    fun editInfo(info: RequestUserInfo){
        viewModelScope.launch {
            RetrofitUtil.USER_SERVICE.editUserInfo(
                ACCESS_TOKEN, RequestUserInfo(info.nickname)
            )
            _user.update {
                it.copy(userNickname = info.nickname)
            }
        }
    }

    fun editPwd(pwd: RequestPassword){
        viewModelScope.launch {
            RetrofitUtil.USER_SERVICE.editPwd(
                token = ACCESS_TOKEN,
                pwd = RequestPassword(pwd.curPwd, pwd.newPwd)
            )
        }
    }

    fun editProfileImg(data: Uri, context: Context){
        val body = UriPartConverter.convertedPart(data, context)
        viewModelScope.launch {
            RetrofitUtil.USER_SERVICE.uploadProfileImage(ACCESS_TOKEN, body)
            _user.update{
                it.copy(profileUrl = data.toString())
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