package com.ssafy.travelcollector.fragment.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentSignUpBinding
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignUpFragment : BaseFragment<FragmentSignUpBinding>(
    FragmentSignUpBinding::bind,
    R.layout.fragment_sign_up
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        val defaultInfo = accountViewModel.getUserInfoToSignUp()

        if(defaultInfo.userEmail.isNotEmpty()){
            binding.signUpEtEMail.setText(defaultInfo.userEmail)
            binding.signUpEtName.setText(defaultInfo.userNickname)
        }

        binding.signUpBtnSignUp.setOnClickListener {
            //db에 회원 정보 저장
            if(isValidInformation()){
                lifecycleScope.launch {
                    val msg = withContext(Dispatchers.IO) {
                        RetrofitUtil.USER_SERVICE.insert(
                            User(
                                userEmail = binding.signUpEtEMail.text.toString(),
                                userPwd = binding.signUpEtPw.text.toString(),
                                userNickname = binding.signUpEtName.text.toString()
                            )
                        ).body()?.get("msg").toString()
                    }
                    if(msg == "succeed"){
                        findNavController().navigate(R.id.loginFragment)
                        showToast("회원가입 성공")
                    }else{
                        showToast("실패ㅠㅠ")
                    }
                }
            }else{
                showToast("정보가 잘못됨")
            }
        }

    }

    private fun isValidInformation(): Boolean {
        return (binding.signUpEtEMail.text!!.isNotEmpty()
                && binding.signUpEtPw.text!!.isNotEmpty()
                && (binding.signUpEtPw2.text.toString() == binding.signUpEtPw.text!!.toString())
                && binding.signUpEtName.text!!.isNotEmpty()
                && binding.signUpEtPhoneNumber.text!!.isNotEmpty())
    }

}