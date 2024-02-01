package com.ssafy.travelcollector.config

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.ssafy.travelcollector.dto.User

private const val TAG = "SNSAuth"
object SNSAuth{

    val serviceTerms = listOf("service")

    interface LoginCallback{
        fun onAlreadySignIn(userInfo: User)
        fun onSignUp(userInfo: User)
    }

    private lateinit var loginCallBack: LoginCallback

    fun setLoginCallBack(loginCallback: LoginCallback){
        this.loginCallBack = loginCallback
    }

    fun disconnect(){
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    fun startKakaoLogin(context: Context, view: View){
        UserApiClient.instance.loginWithKakaoAccount(context, serviceTerms = serviceTerms){ token, error ->
            if(error != null){
                Log.e(TAG, "startKakaoLogin: 실패, $error")
            }
            else if(token!=null){
                Log.i(TAG, "startKakaoLogin: 성공, ${token.accessToken}")

                UserApiClient.instance.me { user, error ->
                    Log.d(TAG, "startKakaoLoginUser: $user")

                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패1", error)
                    }
                    else if (user != null) {
                        loginCallBack.onSignUp(User(
                            userEmail = user.kakaoAccount?.email!!,
                            userNickname = user.properties?.get("nickname")!!,
                        ))
                    }
                }

            }

        }

    }

    fun startNaverLogin(context: Context, view: View){
        var token: String? = ""
        val profileCallback = object: NidProfileCallback<NidProfileResponse> {
            override fun onError(errorCode: Int, message: String) {
                Log.d(TAG, "onError: $errorCode")
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "onFailure: $httpStatus")
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(context, "errorCode: $errorCode \n errorDescription: $errorDescription", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(result: NidProfileResponse) {
                val userId = result.profile?.id
                Log.d(TAG, "onSuccess: $result")
                Log.d(TAG, "onSuccessId: $userId")
                loginCallBack.onSignUp(User(
                    userEmail = result.profile?.email!!,
                    userNickname = result.profile?.nickname!!,
                ))
            }
        }

        val oauthLoginCallback = object: OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) {
                Log.d(TAG, "onError: $errorCode")
                Toast.makeText(context, "error $errorCode \n message $message", Toast.LENGTH_SHORT).show()
                onFailure(errorCode, message)
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Log.d(TAG, "onFailure: $httpStatus")
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(context, "errorCode: $errorCode \n errorDescription: $errorDescription", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess() {
                token = NaverIdLoginSDK.getAccessToken()
                Log.d(TAG, "onSuccess: $token")
                NidOAuthLogin().callProfileApi(profileCallback)
            }

        }

        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)

    }
}