package com.ssafy.travelcollector.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.travelcollector.dto.Board
import com.ssafy.travelcollector.dto.Comment
import com.ssafy.travelcollector.dto.User
import com.ssafy.travelcollector.util.RetrofitUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

    private val _writer = MutableStateFlow(User())
    val writer = _writer.asStateFlow()

    private val _isTravelHeritageBoard = MutableStateFlow(false)
    val isTravelHeritageBoard = _isTravelHeritageBoard.asStateFlow()

    private val _searchTag = MutableStateFlow(listOf<String>())
    val searchTag = _searchTag.asStateFlow()

    private val _postingId = MutableStateFlow(-1)
    val postingId = _postingId.asStateFlow()
    fun setCurPostingId(id: Int){
        Log.d(TAG, "setCurPostingId: adfd")
        _postingId.update { id }}

    fun postBoard(title: String, content: String, images: ArrayList<MultipartBody.Part>, tags: List<String>){
        viewModelScope.launch {
            val id = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.postBoard(
                    AccountViewModel.ACCESS_TOKEN, Board(title, content, tags)
                ).body()!!.id
            }
            if(images.isNotEmpty()){
                RetrofitUtil.BOARD_SERVICE.uploadProfileImage(
                    id = id,
                    images = images.toList(),
                )
            }
        }
    }

    fun deleteBoard(){
        viewModelScope.launch {
            val res = RetrofitUtil.BOARD_SERVICE.deleteBoard(
                AccountViewModel.ACCESS_TOKEN, _boardDetail.value.id
            )
            if(res.code() / 100 == 2){
                loadAllBoards()
            }
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
        val newList = list.sortedByDescending { it.date }
        _boardList.update { ArrayList(newList) }
    }

    fun loadDetailBoard(){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.getBoardDetail(postingId.value)
            }
            if(res.code()/100 == 2){
                Log.d(TAG, "loadDetailBoard ok: $res")
                setCurDetailBoard(res.body()!!)
                loadWriter(res.body()!!.writer)
            }else{
                Log.d(TAG, "loadDetailBoard x: $res")
            }
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
                loadDetailBoard()
//                loadComments(boardId)
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
                loadDetailBoard()
//                loadComments(boardId)
            }
        }
    }

    fun loadComments(boardId: Int){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.getComments(boardId)
            }
            if(res.code()/100 == 2){
                for(comment in res.body()!!){
                    val writerRes = withContext(Dispatchers.IO){
                        RetrofitUtil.USER_SERVICE.getUserInfoById(comment.writerId)
                    }
                    if(writerRes.code() / 100 == 2){
                        Log.d(TAG, "loadComments: ${writerRes.body()}")
                        comment.writerName = writerRes.body()!!.userName
                        comment.writerTitle = writerRes.body()!!.title
                        comment.imgUrl = writerRes.body()!!.profileUrl
                    }
                }
                setComments(ArrayList(res.body()!!))
            }
        }
    }
    fun setComments(list: ArrayList<Comment>){
        _comments.update { list }
    }

    private fun loadWriter(writerId: Int){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.USER_SERVICE.getUserInfoById(writerId)
            }
            if(res.code()/100 == 2){
                setWriter(res.body()!!)
            }
        }
    }

    fun setWriter(user:User){
        _writer.update { user }
    }

    fun initWriter(){
        _writer.update { User() }
    }

    fun setIsHeritageBoard(isHeritageBoard: Boolean){
        _isTravelHeritageBoard.update { isHeritageBoard }
    }

    fun setSearchBoardTags(tags: List<String>){
        _searchTag.update { tags }
    }

    fun searchBoardsByTags(tags: List<String>){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.searchByTag(tags)
            }
            if(res.code()/100 == 2){
                res.body()?.let{ ArrayList(it) }?.let{ setCurBoardList(it) }
            }
        }
    }

    fun searchByWord(word: String){
        viewModelScope.launch {
            val res = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.searchByKeyword(word)
            }
            val res2 = withContext(Dispatchers.IO){
                RetrofitUtil.BOARD_SERVICE.searchByTag(listOf(word))
            }
            if(res.code()/100 == 2 && res2.code()/100 == 2){
                val allList = mutableListOf<Board>()
                allList.apply {
                    addAll(res.body()!!)
                    addAll(res2.body()!!)
                }
                val newList = allList.distinctBy { it.id }
                setCurBoardList(ArrayList(newList))
            }

        }
    }

    fun searchUserHeritagePost(tags: List<String>){
        viewModelScope.launch {
            val res = RetrofitUtil.BOARD_SERVICE.searchUserHeritagePost(tags)
            if(res.code()/100 == 2){
                setCurBoardList(ArrayList(res.body()!!))
            }
        }
    }

}