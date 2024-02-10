package com.ssafy.travelcollector.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewbinding.ViewBinding
import com.ssafy.travelcollector.MainActivity
import com.ssafy.travelcollector.viewModel.AccountViewModel
import com.ssafy.travelcollector.viewModel.AchievementViewModel
import com.ssafy.travelcollector.viewModel.BoardViewModel
import com.ssafy.travelcollector.viewModel.HeritageViewModel
import com.ssafy.travelcollector.viewModel.MainActivityViewModel
import com.ssafy.travelcollector.viewModel.StoreViewModel
import com.ssafy.travelcollector.viewModel.TravelViewModel

// Fragment의 기본을 작성, 뷰 바인딩 활용
abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null

    protected val binding get() = _binding!!

    protected val mainActivityViewModel: MainActivityViewModel by activityViewModels()
    protected val accountViewModel: AccountViewModel by activityViewModels()
    protected val travelViewModel: TravelViewModel by activityViewModels()
    protected val heritageViewModel: HeritageViewModel by activityViewModels()
    protected val boardViewModel: BoardViewModel by activityViewModels()
    protected val storeViewModel: StoreViewModel by activityViewModels()
    protected val achievementViewModel: AchievementViewModel by activityViewModels()

    protected lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}
