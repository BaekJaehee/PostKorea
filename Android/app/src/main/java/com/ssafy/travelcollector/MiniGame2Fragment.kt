package com.ssafy.travelcollector

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMiniGame2Binding


class MiniGame2Fragment : BaseFragment<FragmentMiniGame2Binding>(FragmentMiniGame2Binding::bind, R.layout.fragment_mini_game2) {

    private var heritageSplitEra : List<String> = ArrayList()
    private var era : String? = null
    private var year : Int? = null
    private var life = 10
    private var start = 0
    private var end = 2023
    private var myAnswer : Int? = null
    private var isEnd : Boolean = false




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.miniGameTvStart.setOnClickListener {
            if (!isEnd){
                start()
            }
        }

        binding.miniGameTvSubmit.setOnClickListener {
            if (year != null) {
                onSubmitYear()
            }
        }

        binding.miniGameEtAnswer.setOnEditorActionListener{ textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE){
                onSubmitYear()
                handled = true
            }
            handled
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ccceName = heritageViewModel.curHeritage.value.era
        if (ccceName.contains("(")){
            heritageSplitEra = ccceName.split("(")
            era = heritageSplitEra[0]
            if (heritageSplitEra[1].length < 6 && !heritageSplitEra[1].contains("세기")){
                year = heritageSplitEra[1].split(")")[0].trim().toInt()
            }
        } else {
            heritageSplitEra = listOf(ccceName)
            era = heritageSplitEra[0]
        }
    }
    private fun start(){
        if (year != null){
            binding.miniGameEtAnswer.hint = "연도를 입력해 주세요"
            GuessingYearView()
        } else {

        }
    }

    private fun GuessingYearView(){
        binding.miniGameTvDescription.visibility = View.GONE
        binding.miniGameTvStart.visibility = View.GONE
        binding.miniGameIvMain.visibility = View.GONE

        binding.miniGameIvHeritage.visibility = View.VISIBLE
        Glide.with(this)
            .load(heritageViewModel.curHeritage.value.imageUrl) // 불러올 이미지 url
            .into(binding.miniGameIvHeritage) // 이미지를 넣을 뷰

        binding.miniGameTvUpDown.visibility = View.VISIBLE
        binding.miniGameTvRemainingTries.visibility = View.VISIBLE
        binding.miniGameTvMyAnswer.visibility = View.VISIBLE
        binding.miniGameTvYearRange.visibility = View.VISIBLE
        binding.miniGameTvSubmit.visibility = View.VISIBLE
        binding.miniGameTextInputLayout.visibility = View.VISIBLE
    }

    private fun onSubmitYear(){
        if (binding.miniGameEtAnswer.text!!.isNotEmpty()){
            myAnswer = binding.miniGameEtAnswer.text.toString().toInt()
            if (myAnswer!! in start..end){
                binding.miniGameTvMyAnswer.text = "내가 제출한 연도 : $myAnswer 년"
                upDownCheck()
            } else {
                Toast.makeText(mainActivity, "범위 내 연도를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun upDownCheck() {
        if (myAnswer!! == year!!) {
            succeedGuessingYear()
        } else if (myAnswer!! > year!!) {
            binding.miniGameTvUpDown.text = "Down"
            life -= 1
            end = myAnswer!! - 1
        } else {
            binding.miniGameTvUpDown.text = "Up"
            life -= 1
            start = myAnswer!! + 1
        }
        binding.miniGameTvYearRange.text = "$start 년 ~ $end 년"
        binding.miniGameTvRemainingTries.text = "남은 기회 : $life"

        if (life == 0){
            failGuessingYear()
        }
    }

    private fun succeedGuessingYear() {
        binding.miniGameTvDescription.visibility = View.VISIBLE
        binding.miniGameTvDescription.text = "정답 : ${year.toString()}"
        binding.miniGameTvStart.visibility = View.VISIBLE
        binding.miniGameTvStart.text = " 성 공 "

        binding.miniGameTvUpDown.text = "얻은 포인트 : ${life*10}"
        binding.miniGameTvRemainingTries.visibility = View.GONE
        binding.miniGameTvMyAnswer.visibility = View.GONE
        binding.miniGameTvYearRange.visibility = View.GONE
        binding.miniGameTvSubmit.visibility = View.GONE
        binding.miniGameTextInputLayout.visibility = View.GONE

        isEnd = true
    }

    private fun failGuessingYear() {
        binding.miniGameTvDescription.visibility = View.VISIBLE
        binding.miniGameTvDescription.text = "정답 : ${year.toString()}"
        binding.miniGameTvStart.visibility = View.VISIBLE
        binding.miniGameTvStart.text = " 실 패 "

        binding.miniGameTvUpDown.text = "얻은 포인트 : ${life*10}"
        binding.miniGameTvRemainingTries.visibility = View.GONE
        binding.miniGameTvMyAnswer.visibility = View.GONE
        binding.miniGameTvYearRange.visibility = View.GONE
        binding.miniGameTvSubmit.visibility = View.GONE
        binding.miniGameTextInputLayout.visibility = View.GONE

        isEnd = true
    }
}