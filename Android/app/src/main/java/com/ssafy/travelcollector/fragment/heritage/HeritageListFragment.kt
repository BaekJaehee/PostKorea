package com.ssafy.travelcollector.fragment.heritage

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.HeritageAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentHeritageListBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HeritageListFragment : BaseFragment<FragmentHeritageListBinding>(FragmentHeritageListBinding::bind,
    R.layout.fragment_heritage_list
){
    private val heritageAdapter: HeritageAdapter by lazy{
        HeritageAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private fun initView(){
        lifecycleScope.launch {
            heritageViewModel.loadHeritageList()
        }
    }

    private fun initAdapter () {
        lifecycleScope.launch {
            heritageViewModel.curHeritageList.collect{
                heritageAdapter.submitList(it)
            }
        }
        heritageAdapter.eventListener = object : HeritageAdapter.EventListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {}
            override fun delete(position: Int) {  }
            override fun onRemove(position: Int) {}
            override fun onMove(from: Int, to: Int) {}
            override fun onClick(position: Int) {
                heritageViewModel.loadHeritageDetail(
                    heritageViewModel.curHeritageList.value[position].id
                )
                findNavController().navigate(R.id.culturalAssetDetailFragment)
            }

        }
        binding.heritageListRv.adapter = heritageAdapter
    }

}