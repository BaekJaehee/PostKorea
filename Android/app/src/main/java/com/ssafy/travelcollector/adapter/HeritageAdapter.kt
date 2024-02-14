package com.ssafy.travelcollector.adapter

import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.travelcollector.R
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.config.ITouchMove
import com.ssafy.travelcollector.config.ITouchRemove
import com.ssafy.travelcollector.databinding.HeritageRvItemNoHeartBinding
import com.ssafy.travelcollector.dto.Heritage
import com.ssafy.travelcollector.viewModel.MainActivityViewModel

private const val TAG = "HeritageAdapter"
class HeritageAdapter(val isPlanning: Boolean = false) : BaseAdapter<Heritage>(), ITouchMove {

    private var visitedList = setOf<Int>()
    fun setVisitedList(visited: Set<Int>){
        visitedList = visited
        Log.d(TAG, "setVisitedList: $visited")
    }

    inner class HeritageHolder(private val binding: HeritageRvItemNoHeartBinding):BaseHolder(binding) , View.OnCreateContextMenuListener,
        ITouchRemove {

        override fun bindInfo(data: Heritage) {
            binding.heritageName.text = data.name
            Glide.with(binding.root)
                .load(data.imageUrl)
                .into(binding.heritageImage)
            binding.root.setOnClickListener {
                eventListener.onClick(layoutPosition)
            }
            if(isPlanning){
                if(!data.gameEnable){
                    binding.touchIcon.visibility = View.VISIBLE
                    binding.btnMiniGame.visibility = View.GONE
                    binding.touchIcon.setImageResource(R.drawable.drag_handle)
                    binding.touchIcon.setOnTouchListener{ _, event->
                        if(event.action == MotionEvent.ACTION_DOWN)
                            eventListener.onStartDrag(this)
                        false
                    }
                    binding.root.setBackgroundColor(binding.root.resources.getColor(com.kakao.sdk.friend.R.color.transparent))
                }else{
                    binding.touchIcon.visibility = View.GONE
                    binding.root.setBackgroundColor(binding.root.resources.getColor(R.color.gold))
                }
            }else{
                binding.touchIcon.visibility = View.GONE
                if(visitedList.contains(data.id)){
                    binding.touchIcon.visibility = View.VISIBLE
                    binding.touchIcon.setImageResource(R.drawable.check_green)
                }
            }

            binding.root.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val menuItem = menu.add(0,0,0,"삭제하기")
            menuItem.setOnMenuItemClickListener {
                eventListener.delete(layoutPosition)
                true
            }
        }

        override fun removeItem(position: Int) {
            eventListener.onRemove(position)
        }

    }

    override fun moveItem(from: Int, to: Int){
        eventListener.onMove(from, to)
    }

    interface EventListener{
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
        fun delete(position: Int)
        fun onRemove(position: Int)
        fun onMove(from: Int, to: Int)
        fun onClick(position: Int)
    }

    lateinit var eventListener: EventListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = HeritageRvItemNoHeartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeritageHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }
}