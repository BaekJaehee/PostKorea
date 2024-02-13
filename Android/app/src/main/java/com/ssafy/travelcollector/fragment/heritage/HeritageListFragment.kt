package com.ssafy.travelcollector.fragment.heritage

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.HeritageAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.config.RegionString
import com.ssafy.travelcollector.databinding.FragmentHeritageListBinding
import com.ssafy.travelcollector.util.StringUtil
import kotlinx.coroutines.launch

private const val TAG = "HeritageListFragment"
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

        mainActivityViewModel.setPageTitle("문화재 도감")
        heritageViewModel.loadHeritageList()


        val eraItems = resources.getStringArray(R.array.era)
        val categoryItems = resources.getStringArray(R.array.category)
        binding.heritageListAtvEra.setAdapter(ArrayAdapter(
            requireContext(), R.layout.item_list, eraItems
        ))
        binding.heritageListAtvCategory.setAdapter(
            ArrayAdapter(requireContext(), R.layout.item_list, categoryItems
        ))
        binding.heritageListAtvRegion1.setAdapter(
            ArrayAdapter(requireContext(), R.layout.item_list, RegionString.REGION.map{it.region})
        )
        binding.heritageListAtvRegion1.setOnItemClickListener{ adapterView, _, position: Int, _ ->
            binding.heritageListAtvRegion2.setText("")
            binding.heritageListAtvRegion2.setAdapter(
                ArrayAdapter(requireContext(), R.layout.item_list, RegionString.findByName(adapterView.getItemAtPosition(position).toString()))
            )
        }

        binding.heritageListSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                heritageViewModel.searchHeritageByName(p0!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })



        binding.heritageListBtnSearchCondition.setOnClickListener {
            val era = binding.heritageListAtvEra.text.toString()
            val category = binding.heritageListAtvCategory.text.toString()
            val region1 = binding.heritageListAtvRegion1.text.toString()
            val region2 = binding.heritageListAtvRegion2.text.toString()
            if(era.isEmpty()&&category.isEmpty()&&region1.isEmpty()&&region2.isEmpty()){
                showToast("조건을 입력하세요")
            }else{
                heritageViewModel.searchHeritageList(
                    StringUtil.nullableString(region1),
                    StringUtil.nullableString(region2),
                    StringUtil.nullableString(era),
                    StringUtil.nullableString(category))
            }
        }
    }

    private fun initAdapter () {
        heritageAdapter.setVisitedList(mainActivityViewModel.visitedHeritage.value)
        lifecycleScope.launch {
            heritageViewModel.listPageHeritageList.collect{
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
                    heritageViewModel.listPageHeritageList.value[position].id
                )
                findNavController().navigate(R.id.action_heritageListFragment_to_culturalAssetDetailFragment)
            }

        }
        binding.heritageListRv.adapter = heritageAdapter
    }

}