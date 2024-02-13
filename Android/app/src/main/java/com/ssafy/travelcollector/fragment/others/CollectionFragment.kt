package com.ssafy.travelcollector.fragment.others

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.CollectionAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentCollectionBinding
import kotlinx.coroutines.launch


class CollectionFragment : BaseFragment<FragmentCollectionBinding>(FragmentCollectionBinding::bind, R.layout.fragment_collection) {

    private val collectionAdapter: CollectionAdapter by lazy{ CollectionAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private fun initView(){
        mainActivityViewModel.setPageTitle("수집품")
        storeViewModel.loadCollection()
    }

    private fun initAdapter(){

        collectionAdapter.clickListener = object : CollectionAdapter.ClickListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(acquisition: String, own: Boolean, position: Int) {
                binding.collectionHowAcquisitionProgress.text = acquisition
                binding.collectionBtnDetail.visibility = if(own) View.VISIBLE else View.GONE
                collectionAdapter.setSelectIdx(position)
                collectionAdapter.notifyDataSetChanged()
            }

        }

        lifecycleScope.launch {
            storeViewModel.ownList.collect{
                collectionAdapter.submitList(it)
            }
        }

        binding.collectionRv.adapter = collectionAdapter
    }

}