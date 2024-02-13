package com.ssafy.travelcollector.fragment.account

import android.os.Bundle
import android.util.Log
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

private const val TAG = "SignUpFragment"
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

        if(defaultInfo.memberEmail.isNotEmpty()){
            binding.signUpEtEMail.setText(defaultInfo.memberEmail)
            binding.signUpEtName.setText(defaultInfo.userNickname)
        }

        binding.signUpBtnSignUp.setOnClickListener {
            //db에 회원 정보 저장
            if(isValidInformation()){
                lifecycleScope.launch {
                    val res = withContext(Dispatchers.IO) {
                        RetrofitUtil.USER_SERVICE.insert(
                            User(
                                memberEmail = binding.signUpEtEMail.text.toString(),
                                memberPwd = binding.signUpEtPw.text.toString(),
                                userNickname = binding.signUpEtName.text.toString()
                            )
                        )
                    }
                    if(res.code()/100 == 2){
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