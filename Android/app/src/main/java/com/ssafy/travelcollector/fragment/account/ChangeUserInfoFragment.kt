package com.ssafy.travelcollector.fragment.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentChangeUserInfoBinding
import com.ssafy.travelcollector.dto.RequestPassword
import com.ssafy.travelcollector.dto.RequestUserInfo
import com.ssafy.travelcollector.util.RetrofitUtil
import com.ssafy.travelcollector.viewModel.AccountViewModel.Companion.ACCESS_TOKEN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ChangeUserInfoFragment : BaseFragment<FragmentChangeUserInfoBinding>(FragmentChangeUserInfoBinding::bind, R.layout.fragment_change_user_info){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.changeUserInfoEmail.text = accountViewModel.user.value.memberEmail
        binding.changeUserInfoEtNickname.setText(accountViewModel.user.value.userNickname)
        binding.changeUserInfoBtnSubmit.setOnClickListener {
            lifecycleScope.launch {
                val curPwd = binding.changeUserInfoCurrentPw.text.toString()
                 val responseCode = withContext(Dispatchers.IO){
                     RetrofitUtil.USER_SERVICE.checkPwd(
                         token = ACCESS_TOKEN,
                         check = RequestPassword(curPwd)
                     ).code()
                 }
                if(responseCode/100 == 2){
                    val changePw = binding.changeUserInfoPw.text.toString()
                    val confirmPw = binding.changeUserInfoPwConfirm.text.toString()
                    val nickName = binding.changeUserInfoEtNickname.text.toString()

                    if(changePw.isNotEmpty()){
                        if(changePw.length >= 4 && changePw == confirmPw){
                            accountViewModel.editPwd(RequestPassword(curPwd, changePw))
                            accountViewModel.editInfo(RequestUserInfo(nickName))
                            findNavController().popBackStack()
                        }else{
                            showToast("비밀번호가 4자 이하이거나 확인비밀번호가 다릅니다")
                        }
                    }else{
                        accountViewModel.editInfo(RequestUserInfo(nickName))
                        findNavController().popBackStack()
                    }

                }else{
                    showToast("현재 비밀번호가 다릅니다")
                }
            }
        }
    }
}