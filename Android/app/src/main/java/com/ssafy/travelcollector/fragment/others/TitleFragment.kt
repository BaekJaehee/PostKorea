package com.ssafy.travelcollector.fragment.others

import android.accounts.Account
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.TitleAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentTitleBinding
import com.ssafy.travelcollector.util.RetrofitUtil
import com.ssafy.travelcollector.util.TimeConverter
import com.ssafy.travelcollector.viewModel.AccountViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "TitleFragment"
class TitleFragment: BaseFragment<FragmentTitleBinding>(FragmentTitleBinding::bind, R.layout.fragment_title){

    private val titleAdapter: TitleAdapter by lazy {
        TitleAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private var selectedTitle = -1
    private var ownState = true

    private fun initView(){

        lifecycleScope.launch {
            achievementViewModel.loadAchievement()
            accountViewModel.user.collect{
                binding.titleInUse.text = it.title ?: "칭호 없음"
            }
        }

        binding.titleInHand.setOnClickListener {
            titleAdapter.setSelectIdx(-1)
            selectedTitle = -1
            binding.titleInHand.background.setTint(resources.getColor(R.color.brown2))
            binding.titleNotYet.background.setTint(resources.getColor(R.color.brown3))
            achievementViewModel.setOwnState(true)
            binding.titleBtnUse.visibility = View.VISIBLE
        }
        binding.titleNotYet.setOnClickListener {
            titleAdapter.setSelectIdx(-1)
            selectedTitle = -1
            binding.titleInHand.background.setTint(resources.getColor(R.color.brown3))
            binding.titleNotYet.background.setTint(resources.getColor(R.color.brown2))
            achievementViewModel.setOwnState(false)
            binding.titleBtnUse.visibility = View.GONE
        }

        binding.titleBtnUse.setOnClickListener {
            lifecycleScope.launch {
                if(selectedTitle!= -1){
                    val res = withContext(Dispatchers.IO){
                        RetrofitUtil.VISIT_SERVICE.useTitle(AccountViewModel.ACCESS_TOKEN, selectedTitle)
                    }
                    if(res.code()/100 == 2){
                        accountViewModel.getInfo(AccountViewModel.ACCESS_TOKEN)
                    }
                }

            }
        }
    }

    private fun initAdapter(){

        titleAdapter.eventListener = object: TitleAdapter.EventListener{
            @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
            override fun onClick(title: String, desc: String, date: Long, place: String?, position: Int) {
                binding.titleBtnHowAcquisitionProgress.text = desc
                val dateString = if(ownState) TimeConverter.timeMilliToDateString(date) else "-"
                binding.titleAcquisitionDate.text = "획득날짜 : $dateString"
                binding.titleAcquisitionSite.text = "획득장소 : ${place?:"-"}"
                titleAdapter.setSelectIdx(position)
                titleAdapter.notifyDataSetChanged()
                selectedTitle = if(ownState) achievementViewModel.ownAchievement.value[position].id else achievementViewModel.notOwnAchievement.value[position].id
            }

        }

        lifecycleScope.launch{
            achievementViewModel.ownState.collect{
                state->
                ownState = state
                launch{
                    achievementViewModel.ownAchievement.takeWhile {
                        state
                    }.collect{
                        titleAdapter.submitList(it)
                    }

                    achievementViewModel.notOwnAchievement.takeWhile {
                        !state
                    }.collect{
                        titleAdapter.submitList(it)
                    }
                }
            }
        }


        binding.titleListRvTitle.adapter = titleAdapter
    }

}