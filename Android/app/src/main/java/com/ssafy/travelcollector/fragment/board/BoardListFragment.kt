package com.ssafy.travelcollector.fragment.board

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.BoardAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentBoardListBinding
import kotlinx.coroutines.launch

private const val TAG = "BoardListFragment"
class BoardListFragment : BaseFragment<FragmentBoardListBinding>(FragmentBoardListBinding::bind, R.layout.fragment_board_list){

    private val boardAdapter: BoardAdapter by lazy{
        BoardAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private fun initView(){
        lifecycleScope.launch {
            boardViewModel.loadAllBoards()
        }
    }

    private fun initAdapter(){
        lifecycleScope.launch{
            boardViewModel.boardList.collect{
                boardAdapter.submitList(it)
            }
        }

        boardAdapter.clickListener = object : BoardAdapter.ClickListener{
            override fun onClick(position: Int) {
                boardViewModel.loadDetailBoard(
                    boardAdapter.currentList[position].id
                )
                findNavController().navigate(R.id.action_boardListFragment_to_boardPostFragment)
            }

        }

        binding.boardListRv.adapter = boardAdapter
    }

}