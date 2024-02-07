package com.ssafy.travelcollector

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.adapter.main.MainPostingAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMainBinding
import com.ssafy.travelcollector.dto.Posting
import com.ssafy.travelcollector.viewModel.MainActivityViewModel
import kotlinx.coroutines.launch

private const val TAG = "MainFragment"
class MainFragment : BaseFragment<FragmentMainBinding> (FragmentMainBinding::bind, R.layout.fragment_main){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setNavigationBarStatus(true)

        lifecycleScope.launch{
            accountViewModel.user.collect{
                Log.d(TAG, "onViewCreated: $it")
            }
        }

        initView()

        binding.mainPostRv.adapter = MainPostingAdapter().apply {
            submitList(listOf(Posting(), Posting(), Posting()))
            clickListener = object : MainPostingAdapter.IClickListener{
                override fun onClick(position: Int) {
//                    val curId = mainActivityViewModel.posting.value[position].postId
//                    mainActivityViewModel.setSelectedPostingId(curId)
                    findNavController().navigate(R.id.travelPostEditFragment)
                }
            }
        }
    }

    private fun initView(){
        binding.mainTvBtnBoardShowAll.setOnClickListener {
            findNavController().navigate(R.id.heritagePostFragment)
        }
        binding.mainTvBtnBoardShowAll.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_boardListFragment)
        }
    }


}