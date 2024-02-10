package com.ssafy.travelcollector.fragment.others

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
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
        storeViewModel.loadCollection()
    }

    private fun initAdapter(){
        lifecycleScope.launch {
            storeViewModel.ownProductList.collect{
                collectionAdapter.submitList(it)
            }
        }

        binding.collectionRv.adapter = collectionAdapter
    }

}