package com.ssafy.travelcollector.fragment.others

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.CollectionAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.customView.ProductDetailDialog
import com.ssafy.travelcollector.databinding.FragmentCollectionBinding
import com.ssafy.travelcollector.dto.Product
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
            override fun onClick(data: Product, position: Int) {
                binding.collectionHowAcquisitionProgress.text = data.acquisition
                binding.collectionBtnDetail.visibility = if(data.date != null) View.VISIBLE else View.GONE
                binding.collectionBtnDetail.setOnClickListener {
                    ProductDetailDialog(requireContext(), data).show()
                }
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