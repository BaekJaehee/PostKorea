package com.ssafy.travelcollector

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentMiniGame2Binding
import com.ssafy.travelcollector.viewModel.AccountViewModel
import okhttp3.internal.toImmutableList


private const val TAG = "MiniGameFragment"

class MiniGame2Fragment : BaseFragment<FragmentMiniGame2Binding>(FragmentMiniGame2Binding::bind, R.layout.fragment_mini_game2) {

    private var heritageSplitEra : List<String> = ArrayList()
    private var year : Int? = null
    private var year_start : Int? = null
    private var year_end : Int? = null
    private var myAnswer : Int? = null
    private var isEnd : Boolean = false
    private var life = 15
    private var start = 0
    private var end = 2023
    private var era = mapOf("삼국" to arrayOf(0, 668), "신라" to arrayOf(0, 668), "백제" to arrayOf(0, 660), "고구려" to arrayOf(0, 668), "통일신라" to arrayOf(668, 892), "후삼국" to arrayOf(892, 936), "고려" to arrayOf(936, 1392), "조선" to arrayOf(1392, 1897), "대한제국" to arrayOf(1897, 1910), "일제" to arrayOf(1910, 1945), "현대" to arrayOf(1945, 2024))


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
                binding.miniGameEtAnswer.text = null
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
        if (ccceName!!.contains("(")){
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
                life = 4
                var count = 0
                for (i in era.keys){
                    if (ccceName.contains(i)){
                        count += 1
                    }
                }
                if (ccceName.contains("통일신라")) {
                    if (count == 2){
                        year_start = era["통일신라"]!!.get(0)
                        year_end = era["통일신라"]!!.get(1)
                    } else if (count > 2){
                        for (i in era.keys) {
                            if (i == "신라") continue
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
                } else if (ccceName.contains("후삼국")) {
                    if (count == 2){
                        year_start = era["후삼국"]!!.get(0)
                        year_end = era["후삼국"]!!.get(1)
                    } else if (count > 2){
                        for (i in era.keys) {
                            if (i == "삼국") continue
                            if (ccceName.contains(i)){
                                if (year_start == null){
                                    year_start = era[i]!!.get(1) - 50
                                    year_end = era[i]!!.get(1)
                                } else if (year_start != null){
                                    year_end = era[i]!!.get(0) + 100
                                }
                            }
                        }
                    }
                } else {
                    if (count == 1){
                        for (i in era.keys){
                            if (ccceName.contains(i)){
                                year_start = era[i]?.get(0)
                                year_end = era[i]?.get(1)
                            }
                        }
                    }
                    if (count > 1){
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
                    if (count == 0) {
                        life = 1
                    }
                }
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

        binding.miniGameIvUpDown.visibility = View.VISIBLE
        Glide.with(this)
            .load(R.drawable.arrow_up_down)
            .into(binding.miniGameIvUpDown)

        binding.miniGameTvUpDown.visibility = View.VISIBLE
        binding.miniGameTvRemainingTries.visibility = View.VISIBLE
        binding.miniGameTvRemainingTries.text = "남은 기회 : $life"
        binding.miniGameTvYearRange.visibility = View.VISIBLE
        binding.miniGameTextInputLayout.visibility = View.VISIBLE

        if (life == 1){
            succeedGuessingYear()
            binding.miniGameTvStart.text = "연도 불명확"
        }
    }

    private fun onSubmitYear(){
        binding.miniGameTvUpDown.visibility = View.GONE
        if (binding.miniGameEtAnswer.text!!.isNotEmpty()){
            myAnswer = binding.miniGameEtAnswer.text.toString().toInt()
            if (myAnswer!! in start..end){
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
            Glide.with(this)
                .load(R.drawable.arrow_downward)
                .into(binding.miniGameIvUpDown)
            life -= 1
            end = myAnswer!! - 1
        } else {
            Glide.with(this)
                .load(R.drawable.arrow_upward)
                .into(binding.miniGameIvUpDown)
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
        if (myAnswer!! in year_start!!..year_end!!) {
            succeedGuessingYear()
        } else if (myAnswer!! > year_end!!) {
            Glide.with(this)
                .load(R.drawable.arrow_downward)
                .into(binding.miniGameIvUpDown)
            life -= 1
            end = myAnswer!! - 1
        } else if (myAnswer!! < year_start!!) {
            Glide.with(this)
                .load(R.drawable.arrow_upward)
                .into(binding.miniGameIvUpDown)
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
        binding.miniGameIvUpDown.visibility = View.GONE
        binding.miniGameTvDescription.visibility = View.VISIBLE
        binding.miniGameTvDescription.text = "정답 : ${heritageViewModel.curHeritage.value.era}"
        binding.miniGameTvStart.visibility = View.VISIBLE
        binding.miniGameTvStart.text = " 성 공 "
        binding.miniGameTvUpDown.visibility = View.VISIBLE
        binding.miniGameTvUpDown.text = "얻은 포인트 : ${life*10}"
        binding.miniGameTvYearRange.visibility = View.GONE
        binding.miniGameTvRemainingTries.visibility = View.GONE
        binding.miniGameTextInputLayout.visibility = View.GONE

        heritageViewModel.editPoints(life*10){
            accountViewModel.getInfo(AccountViewModel.ACCESS_TOKEN)
        }

        isEnd = true
    }

    private fun failGuessingYear() {
        binding.miniGameIvUpDown.visibility = View.GONE
        binding.miniGameTvDescription.visibility = View.VISIBLE
        binding.miniGameTvDescription.text = "정답 : ${heritageViewModel.curHeritage.value.era}"
        binding.miniGameTvStart.visibility = View.VISIBLE
        binding.miniGameTvStart.text = " 실 패 "
        binding.miniGameTvRemainingTries.visibility = View.GONE
        binding.miniGameTvYearRange.visibility = View.GONE
        binding.miniGameTextInputLayout.visibility = View.GONE

        isEnd = true
    }
}