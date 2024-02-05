package com.ssafy.travelcollector.fragment.heritage

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.adapter.CommentAdapter
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentHeritagePostBinding
import com.ssafy.travelcollector.dto.Comment

class HeritagePostFragment : BaseFragment<FragmentHeritagePostBinding>(FragmentHeritagePostBinding::bind,
    R.layout.fragment_heritage_post
){

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val bottomSheet by lazy{ binding.root.findViewById<LinearLayout>(R.id.bottom_sheet)}
    private val commentRecyclerView by lazy{binding.root.findViewById<RecyclerView>(R.id.bottom_sheet_rv)}


    private val commentAdapter: CommentAdapter by lazy {
        CommentAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initAdapter()
    }

    private fun initView(){
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        binding.heritagePostEtComment.setOnEditorActionListener { _, id, _ ->
            if(id == EditorInfo.IME_ACTION_DONE){
                addComment()
            }
            true
        }
        binding.heritagePostBtnAddComment.setOnClickListener {
            addComment()
        }
        binding.heritagePostBtnShowAllComments.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initAdapter(){
        commentRecyclerView.adapter = commentAdapter
//        binding.heritagePostRvComment.adapter = commentAdapter
    }

    private fun addComment(){
        var newList = commentAdapter.currentList.toMutableList()
        newList.add(Comment(content = binding.heritagePostEtComment.text.toString()))
        commentAdapter.submitList(newList)
        binding.heritagePostEtComment.setText("")
    }

}