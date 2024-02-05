package com.ssafy.travelcollector.fragment.account

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
//import com.ssafy.travelcollector.StoreDialogFragment
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.config.SNSAuth
import com.ssafy.travelcollector.databinding.FragmentLoginBinding
import com.ssafy.travelcollector.dto.User
import kotlinx.coroutines.launch

private const val TAG = "LoginFragment"

class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::bind,
    R.layout.fragment_login
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setNavigationBarStatus(false)

        setLoginCallBack()

        lifecycleScope.launch {
            accountViewModel.accessToken.collect{
                if(it.isNotEmpty() && it!="null" && accountViewModel.loginResponseCode == 200){
                    lifecycleScope.launch {
                        accountViewModel.getInfo(it)
                        findNavController().navigate(R.id.mainFragment)
                    }
                }else{
                    if(accountViewModel.loginResponseCode != 0){
                        Log.d(TAG, "onViewCreated: ${accountViewModel.loginResponseCode}")
                        showToast("잘못됐습니다")
                    }
                }
            }
        }

        binding.loginBtnLogin.setOnClickListener{
            lifecycleScope.launch {
                try{
                    accountViewModel.login(binding.loginEtId.text.toString(), binding.loginEtPw.text.toString())
                }catch(e: Exception){
                    Log.e(TAG, "onViewCreated: $e",)
                }
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

//        binding.modalTest.setOnClickListener {
//            val dlg = StoreDialogFragment(requireContext())
//            dlg.show("hi")
//        }

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