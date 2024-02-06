package com.ssafy.travelcollector

import android.os.Bundle
import android.view.View
import com.ssafy.travelcollector.config.BaseFragment
import com.ssafy.travelcollector.databinding.FragmentGameBinding

class GameFragment : BaseFragment<FragmentGameBinding>(FragmentGameBinding::bind, R.layout.fragment_game) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}