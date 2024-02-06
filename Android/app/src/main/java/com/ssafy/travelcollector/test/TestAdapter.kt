package com.ssafy.travelcollector.test

import android.annotation.SuppressLint
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Motion
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ssafy.travelcollector.config.BaseAdapter
import com.ssafy.travelcollector.config.ITouchMove
import com.ssafy.travelcollector.config.ITouchRemove
import com.ssafy.travelcollector.databinding.TestBinding
import java.util.Collections

class TestAdapter: BaseAdapter<TDto>(), ITouchMove{

    inner class TestHolder(private val binding: TestBinding): BaseHolder(binding), View.OnCreateContextMenuListener, ITouchRemove{
        override fun bindInfo(data: TDto) {
            binding.testing.text = data.a.toString()
            binding.testDrag.setOnTouchListener{ _, event->
                if(event.action == MotionEvent.ACTION_DOWN)
                    startDragListener.onStartDrag(this)
                false
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val menuItem = menu.add(0,0,0,"delete")
            menuItem.setOnMenuItemClickListener {
                deleteListener.delete(layoutPosition)
                true
            }
        }

        override fun removeItem(position: Int){
            removeListener.onRemove(position)
        }

    }


    override fun moveItem(from: Int, to: Int){
        moveListener.onMove(from, to)
    }

    interface StartDragListener{
        fun onStartDrag(viewHolder: ViewHolder)
    }

    lateinit var startDragListener: StartDragListener

    interface DeleteListener{
        fun delete(position: Int)
    }

    lateinit var deleteListener: DeleteListener

    interface RemoveListener{
        fun onRemove(position: Int)
    }

    lateinit var removeListener: RemoveListener

    interface MoveListener{
        fun onMove(from: Int, to: Int)
    }

    lateinit var moveListener: MoveListener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        val binding = TestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TestHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bindInfo(getItem(position))
    }

}