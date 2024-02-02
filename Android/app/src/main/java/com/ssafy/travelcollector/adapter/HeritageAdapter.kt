package com.ssafy.travelcollector.adapter

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.config.ITouchMove
import com.ssafy.travelcollector.config.ITouchRemove
import com.ssafy.travelcollector.databinding.HeritageRvItemNoHeartBinding
import com.ssafy.travelcollector.dto.Heritage

class HeritageAdapter : BaseAdapter<Heritage>(), ITouchMove {

    inner class HeritageHolder(private val binding: HeritageRvItemNoHeartBinding):BaseHolder(binding) , View.OnCreateContextMenuListener,
        ITouchRemove {
        override fun bindInfo(data: Heritage) {
            binding.heritageName.text = data.name
            binding.root.setOnClickListener {
                eventListener.onClick(layoutPosition)
            }
        }

        override fun onCreateContextMenu(
            p0: ContextMenu?,
            p1: View?,
            p2: ContextMenu.ContextMenuInfo?
        ) {
        }

        override fun removeItem(position: Int) {
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