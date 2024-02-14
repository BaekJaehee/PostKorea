package com.ssafy.travelcollector

import android.os.Bundle
import android.view.View
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentGameBinding
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.dto.Heritage
import kotlinx.coroutines.launch
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

private const val TAG = "GameFragment"

class GameFragment : BaseFragment<FragmentGameBinding>(FragmentGameBinding::bind, R.layout.fragment_game), View.OnClickListener {

    private var currentStage : Int = 0
    private var selectedOption : TextView? = null
    private var correctAnswers : Int = 0
    private var isSubmit : Boolean = false
    private var heritageList : MutableList<Heritage> = ArrayList()
    private var heritageName : MutableList<Heritage> = ArrayList()
    private var heritageCategory : MutableList<Heritage> = ArrayList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.gameTvNext.text = "시작"

        binding.gameTvCorrect.setOnClickListener(this)
        binding.gameTvWrong.setOnClickListener(this)
        binding.gameTvNext.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            launch {
                val heritage = heritageViewModel.curHeritage.value
                var name = ""
                if (heritage.name.split(" ").size == 1) {
                    name = heritage.name
                } else {
                    name = heritage.name.split(" ")[0] +" "+ heritage.name.split(" ")[1]
                }
                heritageViewModel.searchHeritageByName(name)
                heritageViewModel.heritageListByName.collect{
                    it.shuffle()
                    heritageName = it
                }
            }

            launch{
                val category = heritageViewModel.curHeritage.value.category
                val limit = 100
                heritageViewModel.searchHeritageListForGame(category, limit)
                heritageViewModel.curHeritageList.collect{
                    it.shuffle()
                    heritageCategory = it
                }
            }
        }
    }

    private fun findDistance(lat: Double, lng: Double): Double {
        val R = 6371.0
        val heritage = heritageViewModel.curHeritage.value
        val curLat = heritage.lat.toDouble()
        val curLng = heritage.lng.toDouble()
        val dlat = Math.toRadians(lat - curLat)
        val dlng = Math.toRadians(lng - curLng)
        val originLat = Math.toRadians(curLat);
        val destinationLat = Math.toRadians(lat);
        val a = sin(dlat / 2).pow(2) + cos(originLat) * cos(destinationLat) * sin(dlng / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c
    }

    private fun start() {
        binding.gameTvNext.text = "제출"
        currentStage += 1
        binding.gameProgressBar.progress = currentStage
        binding.gameTvProgress.text = "$currentStage/10"
        binding.gameTvCorrect.visibility = View.VISIBLE
        binding.gameTvWrong.visibility = View.VISIBLE
        binding.gameIvHeritage.visibility = View.VISIBLE

//        heritageName = heritageName.filter {
//            it.lat != "0"
//        }.toMutableList()

        heritageName = heritageName.filter {
            findDistance(it.lat.toDouble(), it.lng.toDouble()) < 0.5
        }.toMutableList()

        if (heritageName.size > 4){
            for (i in 0..4){
                if (heritageName[i].imageUrl != null){
                    heritageList.add(heritageName[i])
                } else {
                    heritageList.add(heritageCategory[i])
                }
                heritageList.add(heritageCategory[i])
            }
        } else {
            for (i in 0..heritageName.size-1) {
                if (heritageName[i].imageUrl != null) {
                    heritageList.add(heritageName[i])
                } else {
                    continue
                }
            }
            for (i in 1..(10-heritageList.size)){
                heritageList.add(heritageCategory[i])
            }
        }

        heritageList.shuffle()

        setImage()
    }

    private fun setImage() {
        isSubmit = false
        defaultView()
        Glide.with(this)
            .load(heritageList!![currentStage-1].imageUrl) // 불러올 이미지 url
            .into(binding.gameIvHeritage) // 이미지를 넣을 뷰
        binding.gameTvNext.background = ContextCompat.getDrawable(mainActivity, R.drawable.bg_round_grey)
        selectedOption = null
    }

    private fun defaultView() {
        binding.gameTvCorrect.background = ContextCompat.getDrawable(mainActivity, R.drawable.bg_round_brown)
        binding.gameTvWrong.background = ContextCompat.getDrawable(mainActivity, R.drawable.bg_round_brown)
        binding.gameTvNext.text = "제출"
    }

    private fun selectOXView(tv : TextView) {
        defaultView()
        tv.background = ContextCompat.getDrawable(mainActivity, R.drawable.selected_option_border_bg)
        binding.gameTvNext.background = ContextCompat.getDrawable(mainActivity, R.drawable.bg_round_brown)
    }

    private fun correctView(tv : TextView) {
        tv.background = ContextCompat.getDrawable(mainActivity, R.drawable.correct_option_border_bg)
    }

    private fun wrongView(tv : TextView) {
        tv.background = ContextCompat.getDrawable(mainActivity, R.drawable.wrong_option_border_bg)
    }

    private fun onSubmit(tv : TextView) {
        val heritage : Heritage = heritageList[currentStage - 1]
        val distance = findDistance(heritage.lat.toDouble(), heritage.lng.toDouble())
        if (distance < 0.5) {
            if (selectedOption == binding.gameTvCorrect) {
                correctAnswers += 1
                correctView(selectedOption!!)
            } else {
                correctView(binding.gameTvCorrect)
                wrongView(selectedOption!!)
            }
        } else {
            if (selectedOption == binding.gameTvWrong) {
                correctAnswers += 1
                correctView(selectedOption!!)
            } else {
                correctView(binding.gameTvWrong)
                wrongView(selectedOption!!)
            }
        }
        binding.gameTvNext.text = "다음"
        selectedOption = null
        isSubmit = true
    }

    private fun nextStage() {
        currentStage += 1
        binding.gameProgressBar.progress = currentStage
        binding.gameTvProgress.text = "$currentStage/10"
    }

    private fun end() {
        binding.gameTvCorrect.visibility = View.GONE
        binding.gameTvWrong.visibility = View.GONE
        binding.gameIvHeritage.visibility = View.GONE
        binding.gameTvQuestionUpper.text = "수고하셨습니다"
        binding.gameTvQuestionLower.text = "획득한 포인트 : ${correctAnswers*10}"
        binding.gameTvQuestionNumber.visibility = View.GONE
        binding.gameTvNext.text = "맞은 문제 : $correctAnswers / 10"

        heritageViewModel.editPoints(correctAnswers*10)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.game_tv_correct -> {
                if (!isSubmit){
                    selectOXView(binding.gameTvCorrect)
                    selectedOption = binding.gameTvCorrect
                }
            }

            R.id.game_tv_wrong -> {
                if (!isSubmit){
                    selectOXView(binding.gameTvWrong)
                    selectedOption = binding.gameTvWrong
                }
            }

            R.id.game_tv_next -> {
                if (currentStage == 0) {
                    start()
                } else if (currentStage < 10) {
                    if (selectedOption != null) {
                        onSubmit(selectedOption!!)
                    } else {
                        if (isSubmit){
                            nextStage()
                            setImage()
                        }
                    }
                } else {
                    if (selectedOption != null) {
                        onSubmit(selectedOption!!)
                    } else {
                        if (isSubmit){
                            end()
                        }
                    }
                }
            }
        }
    }
}