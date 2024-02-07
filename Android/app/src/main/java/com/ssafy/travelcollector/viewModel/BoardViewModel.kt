package com.ssafy.travelcollector.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.travelcollector.dto.Board
import com.ssafy.travelcollector.dto.Comment
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody

private const val TAG = "BoardViewModel"
class BoardViewModel: ViewModel() {

    private val _boardList = MutableStateFlow(arrayListOf<Board>())
    val boardList = _boardList.asStateFlow()

    private val _boardDetail = MutableStateFlow(Board())
    val boardDetail = _boardDetail.asStateFlow()

    private val _comments = MutableStateFlow(arrayListOf<Comment>())
    val comments = _comments.asStateFlow()

    fun postBoard(title: String, content: String, images: ArrayList<MultipartBody.Part>){
        viewModelScope.launch {
            RetrofitUtil.BOARD_SERVICE.uploadProfileImage(
                id = withContext(Dispatchers.IO){
                    RetrofitUtil.BOARD_SERVICE.postBoard(
                        AccountViewModel.ACCESS_TOKEN, Board(title, content)
                    ).body()!!.id
                },
                images = images.toList()
            )
        }
    }

    fun loadAllBoards(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.getAllBoards()
            }
            res.body()?.let{ ArrayList(it) }?.let{ setCurBoardList(it) }
        }
    }

    fun setCurBoardList(list:ArrayList<Board>){
        _boardList.update { list }
    }

    fun loadDetailBoard(id: Int){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.getBoardDetail(id)
            }
            setCurDetailBoard(res.body()!!)
        }
    }

    fun setCurDetailBoard(board: Board){
        _boardDetail.update { board }
    }

    fun addComment(boardId: Int, comment: Comment){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.postComment(
                    AccountViewModel.ACCESS_TOKEN, boardId, comment
                )
            }
            if(res.code()/100 == 2){
                loadComments(boardId)
            }
        }
    }

    fun deleteComment(boardId: Int, commentId: Int){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.deleteComment(
                    AccountViewModel.ACCESS_TOKEN, boardId, commentId
                )
            }
            if(res.code()/100 == 2){
                loadComments(boardId)
            }
        }
    }

    fun loadComments(boardId: Int){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.getComments(boardId)
            }
            setComments(ArrayList(res.body()!!))
        }
    }

    fun setComments(list: ArrayList<Comment>){
        _comments.update { list }
    }


}