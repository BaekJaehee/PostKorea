package com.ssafy.travelcollector

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMiniGame2Binding
import okhttp3.internal.toImmutableList


class MiniGame2Fragment : BaseFragment<FragmentMiniGame2Binding>(FragmentMiniGame2Binding::bind, R.layout.fragment_mini_game2) {

    private var heritageSplitEra : List<String> = ArrayList()
    private var year : Int? = null
    private var year_start : Int = 0
    private var year_end : Int = 2023
    private var myAnswer : Int? = null
    private var isEnd : Boolean = false
    private var life = 15
    private var start = 0
    private var end = 2023
    private var era = mapOf("삼국" to arrayOf(0, 668), "신라" to arrayOf(0, 668), "백제" to arrayOf(0, 660), "고구려" to arrayOf(0, 668), "통일신라" to arrayOf(668, 935), "고려" to arrayOf(936, 1392), "조선" to arrayOf(1392, 1897), "대한제국" to arrayOf(1897, 1910))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.miniGameTvStart.setOnClickListener {
            if (!isEnd){
                start()
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
        initiate()
    }

    private fun initiate(){
        val ccceName = heritageViewModel.curHeritage.value.era
        if (ccceName.contains("(")){
            heritageSplitEra = ccceName.split("(")
            if (!heritageSplitEra[1].contains("세기") && !heritageSplitEra[1].contains("~")){
                val year_string = heritageSplitEra[1].split(")")[0].trim()
                if (year_string.contains("년경")){
                    year = year_string.split("년경")[0].toInt()
                } else {
                    year = heritageSplitEra[1].split(")")[0].trim().toInt()
                }
            } else if (heritageSplitEra[1].contains("세기") && !heritageSplitEra[1].contains("~")){
                if (heritageSplitEra[1].contains(",")){
                    val cen1 = heritageSplitEra[1].split(",")[0].toInt()
                    val cen2 = heritageSplitEra[1].split(",")[1].split("세기")[0].trim().toInt()
                    year_start = (cen1 - 1) * 100 + 1
                    year_end = cen2 * 100
                    life = 4
                } else {
                    val cen = heritageSplitEra[1].split("세기")[0].trim().toInt()
                    year_start = (cen - 1) * 100 + 1
                    year_end = cen * 100
                    life = 6
                }
            } else if (!heritageSplitEra[1].contains("세기") && heritageSplitEra[1].contains("~")){
                year_start = heritageSplitEra[1].split("~")[0].trim().toInt()
                year_start = heritageSplitEra[1].split("~")[1].trim().toInt()
                life = 8
            } else if (heritageSplitEra[1].contains("세기") && heritageSplitEra[1].contains("~")){
                val cenList = heritageSplitEra[1].split("~")
                val cen1 = cenList[0].split("세기")[0].trim().toInt()
                val cen2 = cenList[1].split("세기")[0].trim().toInt()
                year_start = (cen1 - 1) * 100 + 1
                year_end = cen2 * 100
                life = 4
            }
        } else {
            if (ccceName.contains("세기")){
                heritageSplitEra = ccceName.split(" ")
                heritageSplitEra.forEach {
                    if (it.contains("세기")){
                        val cen = it.split("세기")[0].trim().toInt()
                        year_start = (cen - 1) * 100 + 1
                        year_end = cen * 100
                        life = 6
                    }
                }
            } else {
                val temp = mutableListOf<String>()
                for (i in era.keys){
                    if (temp.size == 1 && temp[0] == "신라" && i == "통일신라") {
                        temp[0] = i
                    } else {
                        temp.add(i)
                    }
                }
                if (temp.size == 1) {
                    for (i in era.keys) {
                        if (ccceName.contains(i)){
                            year_start = era[i]!!.get(0)
                            year_end = era[i]!!.get(1)
                        }
                    }
                } else if (temp.size == 2) {
                    for (i in era.keys) {
                        if (ccceName.contains(i)){
                            if (year_start == null){
                                year_start = era[i]!!.get(1) - 100
                                year_end = era[i]!!.get(1)
                            } else if (year_start != null){
                                year_end = era[i]!!.get(0) + 100
                            }
                        }
                    }
                }
                life = 4
            }
        }
    }

    private fun start(){
        binding.miniGameEtAnswer.hint = "연도를 입력해 주세요"
        guessingYearView()
    }

    private fun guessingYearView(){
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
        binding.miniGameTextInputLayout.visibility = View.VISIBLE
    }

    private fun onSubmitYear(){
        if (binding.miniGameEtAnswer.text!!.isNotEmpty()){
            myAnswer = binding.miniGameEtAnswer.text.toString().toInt()
            if (myAnswer!! in start..end){
                binding.miniGameTvMyAnswer.text = "내가 제출한 연도 : $myAnswer 년"
                if (year != null){
                    yearUpDownCheck()
                } else {
                    yearRangeUpDownCheck()
                }
            } else {
                Toast.makeText(mainActivity, "범위 내 연도를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun yearUpDownCheck() {
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

    private fun yearRangeUpDownCheck() {
        if (myAnswer!! in year_start..year_end) {
            succeedGuessingYear()
        } else if (myAnswer!! > year_end) {
            binding.miniGameTvUpDown.text = "Down"
            life -= 1
            end = myAnswer!! - 1
        } else if (myAnswer!! < year_start) {
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
        binding.miniGameTvDescription.text = "정답 : ${heritageViewModel.curHeritage.value.era}"
        binding.miniGameTvStart.visibility = View.VISIBLE
        binding.miniGameTvStart.text = " 성 공 "

        binding.miniGameTvUpDown.text = "얻은 포인트 : ${life*10}"
        binding.miniGameTvRemainingTries.visibility = View.GONE
        binding.miniGameTvMyAnswer.visibility = View.GONE
        binding.miniGameTvYearRange.visibility = View.GONE
        binding.miniGameTextInputLayout.visibility = View.GONE

        isEnd = true
    }

    private fun failGuessingYear() {
        binding.miniGameTvDescription.visibility = View.VISIBLE
        binding.miniGameTvDescription.text = "정답 : ${heritageViewModel.curHeritage.value.era}"
        binding.miniGameTvStart.visibility = View.VISIBLE
        binding.miniGameTvStart.text = " 실 패 "

        binding.miniGameTvUpDown.text = "얻은 포인트 : ${life*10}"
        binding.miniGameTvRemainingTries.visibility = View.GONE
        binding.miniGameTvMyAnswer.visibility = View.GONE
        binding.miniGameTvYearRange.visibility = View.GONE
        binding.miniGameTextInputLayout.visibility = View.GONE

        isEnd = true
    }
}