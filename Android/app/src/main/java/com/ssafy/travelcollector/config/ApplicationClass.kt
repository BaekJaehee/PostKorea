package com.ssafy.travelcollector.config

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.navercorp.nid.NaverIdLoginSDK
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.prefs.Preferences


private const val TAG = "ApplicationClass"
class ApplicationClass : Application() {

    val gson: Gson = GsonBuilder().setLenient().create()

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .build()

    override fun onCreate() {
        super.onCreate()

        TedPermission.create()
            .setPermissionListener(object: PermissionListener{
                override fun onPermissionGranted() {
                    Toast.makeText(applicationContext, "gps 권한 획득", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(applicationContext, "gps 권한 획득 실패", Toast.LENGTH_SHORT).show()
                }

            }).setDeniedMessage("위치 정보 권한을 허용해주세요")
            .setPermissions(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ).check()



        NaverIdLoginSDK.initialize(applicationContext, NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, NAVER_CLIENT_NAME)
        KakaoSdk.init(applicationContext, KAKAO_APP_KEY)

        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    companion object{
        const val NAVER_CLIENT_ID = "ISnINdg5vxbmmrhbZ2rJ"
        const val NAVER_CLIENT_SECRET = "Zc1XDYhfgo"
        const val NAVER_CLIENT_NAME = "dMoblie"
        const val KAKAO_APP_KEY = "6bc61f45686049a50d5900bd5a6c330b"

        const val SERVER_URL = "http://i10d102.p.ssafy.io:8080/"

        lateinit var retrofit: Retrofit


    }


}