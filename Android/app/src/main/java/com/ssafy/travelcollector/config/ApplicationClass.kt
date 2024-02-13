package com.ssafy.travelcollector.config

import android.annotation.SuppressLint
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK
import com.ssafy.travelcollector.config.geofence.GeofenceBroadcastReceiver
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import java.util.prefs.Preferences


private const val TAG = "ApplicationClass"
class ApplicationClass : Application() {

    val gson: Gson = GsonBuilder().setLenient().create()

    val loggingInterceptor = HttpLoggingInterceptor().apply{
        level = HttpLoggingInterceptor.Level.BODY
    }

//    val errorInterceptor = Interceptor{
//        val request = it.request()
//        val response = it.proceed(request)
//        if(!response.isSuccessful){
//            Toast.makeText(applicationContext, "통신 오류", Toast.LENGTH_SHORT).show()
//            Log.d(TAG, "abcd: $response")
//        }
//        response
//    }

    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(loggingInterceptor)
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
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ).check()



        NaverIdLoginSDK.initialize(applicationContext, NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, NAVER_CLIENT_NAME)
        KakaoSdk.init(applicationContext, KAKAO_APP_KEY)

        retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(nullOnEmptyConverterFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }


    private val nullOnEmptyConverterFactory = object : Converter.Factory() {
        fun converterFactory() = this
        override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
            val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
            override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
                try{
                    nextResponseBodyConverter.convert(value)
                }catch (e:Exception){
                    e.printStackTrace()
                    null
                }
            } else{
                null
            }
        }
    }

    init{
        instance = this
    }

    companion object{
        const val NAVER_CLIENT_ID = "ISnINdg5vxbmmrhbZ2rJ"
        const val NAVER_CLIENT_SECRET = "Zc1XDYhfgo"
        const val NAVER_CLIENT_NAME = "dMoblie"
        const val KAKAO_APP_KEY = "6bc61f45686049a50d5900bd5a6c330b"

        const val SERVER_URL = "http://i10d102.p.ssafy.io:8080/"

        lateinit var retrofit: Retrofit

        private var instance: ApplicationClass? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

    }


}