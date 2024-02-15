package com.ssafy.travelcollector.fragment.account

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.ApplicationClass
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.config.LoginUserManager
import com.ssafy.travelcollector.config.SNSAuth
import com.ssafy.travelcollector.databinding.FragmentLoginBinding
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.viewModel.AccountViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import okhttp3.internal.notify

private const val TAG = "LoginFragment"

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
) {

    private val manager: LoginUserManager by lazy{ LoginUserManager(ApplicationClass.applicationContext())}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.setNavigationBarStatus(false)
        setLoginCallBack()

        lifecycleScope.launch {
            launch {
                manager.getIsLogin().collectLatest{
                    if(it){
                        LoginUserManager.isWhileLogin = true
                        manager.getToken().collectLatest{ info->
                            if(info[0].isNotEmpty() && info[1].isNotEmpty()){
                                Log.d(TAG, "onViewCreated: aaa")
                                accountViewModel.login(info[0], info[1])
                            }
                        }
                    }else{
                        LoginUserManager.isWhileLogin = false
                        Log.d(TAG, "onViewCreated: login xx")
                    }
                }
            }

            launch {
                accountViewModel.accessToken.collect{
                    if(it.isNotEmpty() && it!="Bearer " && it!= "null" && accountViewModel.loginResponseCode / 100 == 2){
                        lifecycleScope.launch {
                            accountViewModel.getInfo(it)
                        }
                    }else{
                        if(accountViewModel.loginResponseCode / 100 != 2){
                            Log.d(TAG, "onViewCreated: $it \n ${accountViewModel.loginResponseCode}")
//                            showToast("잘못됐습니다")
                        }
                        manager.deleteToken()
                    }
                }
            }

            launch {
                repeatOnLifecycle(Lifecycle.State.STARTED){
                    launch {
                        accountViewModel.user.collect{
                            if(it.memberEmail!=AccountViewModel.DEFAULT_EMAIL)
                                findNavController().navigate(R.id.mainFragment)
                        }
                    }
                }

            }

        }

        binding.loginBtnLogin.setOnClickListener{
            try{
//                accountViewModel.login(binding.loginEtId.text.toString(), binding.loginEtPw.text.toString())
                lifecycleScope.launch {
                    manager.saveToken(
                        listOf(binding.loginEtId.text.toString(), binding.loginEtPw.text.toString())
                    ) 
                }

            }catch(e: Exception){
                Log.e(TAG, "onViewCreated: $e",)
            }
        }

        binding.loginBtnNaverLogin.setOnClickListener{
            SNSAuth.startNaverLogin(requireContext(), view)
        }

        binding.loginKakaoLogin.setOnClickListener{
            SNSAuth.startKakaoLogin(requireContext(), view)
        }

        binding.loginBtnFindId.setOnClickListener {
            SNSAuth.disconnect()
        }

        binding.loginBtnSignUp.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }
    }

    private fun setLoginCallBack(){

        SNSAuth.setLoginCallBack(object : SNSAuth.LoginCallback {
            override fun onAlreadySignIn(userInfo: User) {
            }

            override fun onSignUp(userInfo: User) {
                accountViewModel.passUserInfoToSignUp(userInfo)
                findNavController().navigate(R.id.signUpFragment)
            }

        })

    }

}