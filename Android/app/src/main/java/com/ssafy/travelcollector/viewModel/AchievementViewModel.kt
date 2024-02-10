package com.ssafy.travelcollector.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.travelcollector.dto.Achievement
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "AchievementViewModel"
class AchievementViewModel : ViewModel() {

    private val _ownState = MutableStateFlow(true)
    val ownState = _ownState.asStateFlow()

    fun setOwnState(isOwned: Boolean){
        _ownState.update { isOwned }
    }

    private val _ownAchievement = MutableStateFlow(listOf<Achievement>())
    val ownAchievement = _ownAchievement.asStateFlow()

    private val _notOwnAchievement = MutableStateFlow(listOf<Achievement>())
    val notOwnAchievement = _notOwnAchievement.asStateFlow()

    fun loadAchievement(){
        viewModelScope.launch {
            _ownAchievement.update {
                RetrofitUtil.VISIT_SERVICE.getAchieve(AccountViewModel.ACCESS_TOKEN).body()!!
            }
            _notOwnAchievement.update {
                RetrofitUtil.VISIT_SERVICE.getAchieve(AccountViewModel.ACCESS_TOKEN, false).body()!!
            }
        }
    }

}