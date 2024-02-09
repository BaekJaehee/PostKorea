package com.ssafy.travelcollector

import android.os.Bundle
import android.util.Log
import android.view.View
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentGameBinding
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.util.Constants
import kotlinx.coroutines.launch

private const val TAG = "GameFragment"

class GameFragment : BaseFragment<FragmentGameBinding>(FragmentGameBinding::bind, R.layout.fragment_game), View.OnClickListener {

    private val imageList = Constants.getImage()
    private var currentStage : Int = 0
    private val currentLocation : Int = 1
    private var selectedOption : TextView? = null
    private var correctAnswers : Int = 0
    private var isSubmit : Boolean = false
    private var heritageList : MutableList<Heritage> = ArrayList(10)
    private var heritageName : MutableList<Heritage> = ArrayList(5)
    private var heritageCategory : MutableList<Heritage> = ArrayList(5)

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
                val name = "부석사"
                heritageViewModel.searchHeritageByName(name)
                heritageViewModel.heritageListByName.collect{
                    it.shuffle()
                    heritageName = it.toMutableList()
                }
            }

            launch{
                val category = "불교"
                heritageViewModel.searchHeritageListRandom(
                    null, null, null, category
                )
                heritageViewModel.curHeritageList.collect{
                    heritageCategory = it.toMutableList()
                }
            }

        }
    }





    private fun start() {
        binding.gameTvNext.text = "제출"
        currentStage += 1
        binding.gameProgressBar.progress = currentStage
        binding.gameTvProgress.text = "$currentStage/10"
        binding.gameTvCorrect.visibility = View.VISIBLE
        binding.gameTvWrong.visibility = View.VISIBLE
        binding.gameIvHeritage.visibility = View.VISIBLE

        Log.d(TAG, "onCreate: ${heritageName} ${heritageCategory}")
        heritageList.addAll(heritageName)
        heritageList.addAll(heritageCategory)
        heritageList.shuffle()

        setImage()
    }

    private fun setImage() {
        isSubmit = false
        defaultView()
        Glide.with(this)
            .load(heritageList!![currentStage-1].imageUrl) // 불러올 이미지 url
            .into(binding.gameIvHeritage) // 이미지를 넣을 뷰
//        val heritageImage : HeritageImage = imageList[currentStage - 1]
//        binding.gameIvHeritage.setImageResource(heritageImage.image)
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
        val heritageImage : HeritageImage = imageList[currentStage - 1]
        if (heritageImage.id == currentLocation ) {
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
        binding.gameTvQuestionLower.text = "획득한 포인트 : $correctAnswers"
        binding.gameTvQuestionNumber.visibility = View.GONE
        binding.gameTvNext.text = "맞은 문제 : $correctAnswers / 10"
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