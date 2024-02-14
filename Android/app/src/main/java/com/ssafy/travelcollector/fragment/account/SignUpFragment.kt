package com.ssafy.travelcollector.fragment.account

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentSignUpBinding
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.util.RetrofitUtil
import com.ssafy.travelcollector.util.StringUtil
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
        mainActivityViewModel.setPageTitle("회원 가입")

        val defaultInfo = accountViewModel.getUserInfoToSignUp()

        if(defaultInfo.memberEmail.isNotEmpty()){
            binding.signUpEtEMail.setText(defaultInfo.memberEmail)
            binding.signUpEtName.setText(defaultInfo.userNickname)
        }

        binding.signUpEtEMail.apply{
            addTextChangedListener {
                doAfterTextChanged {
                    if(!StringUtil.emailCheck(this.text.toString())){
                        binding.signUpTvAlertEmail.visibility = View.VISIBLE
                    }else{
                        binding.signUpTvAlertEmail.visibility = View.GONE
                    }
                }
                doOnTextChanged { _, _, _, _ ->
                    binding.signUpTvAlertEmail.visibility = View.GONE
                }
            }
        }

        binding.signUpEtPw.apply{
            addTextChangedListener {
                doAfterTextChanged {
                    if(it.toString().length<4){
                        binding.signUpTvAlertPw.visibility = View.VISIBLE
                    }else{
                        binding.signUpTvAlertPw.visibility = View.GONE
                    }
                }
            }
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
                && StringUtil.emailCheck(binding.signUpEtEMail.text.toString())
                && binding.signUpEtPw.text!!.length >=4
                && (binding.signUpEtPw2.text.toString() == binding.signUpEtPw.text!!.toString())
                && binding.signUpEtName.text!!.isNotEmpty()
                && binding.signUpEtPhoneNumber.text!!.isNotEmpty())
    }

}