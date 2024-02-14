package com.ssafy.travelcollector.fragment.board

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
        mainActivityViewModel.setPageTitle("게시판")

        lifecycleScope.launch {
            boardViewModel.searchTag.collect{
//                boardViewModel.searchBoardsByTags(it)
                boardViewModel.searchUserHeritagePost(it)
            }

        }

        binding.boardListAddPost.setOnClickListener{
            boardViewModel.setIsHeritageBoard(false)
            findNavController().navigate(R.id.travelPostEditFragment)
        }

        binding.boardListSv.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                boardViewModel.searchByWord(p0!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    private fun initAdapter(){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                launch {
                    if(boardViewModel.searchTag.value.isEmpty()){
                        boardViewModel.loadAllBoards()
                    }
                }
                launch {
                    boardViewModel.boardList.collect{
                        boardAdapter.submitList(it)
                    }
                }
            }

        }

        boardAdapter.clickListener = object : BoardAdapter.ClickListener{
            override fun onClick(id: Int) {
                boardViewModel.setCurPostingId(id)
                findNavController().navigate(R.id.boardPostFragment)
            }

        }

        binding.boardListRv.adapter = boardAdapter
    }

}