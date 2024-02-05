package com.ssafy.travelcollector.config

import android.widget.ListAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchCallBack: ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        (recyclerView.adapter as ITouchMove).moveItem(
            viewHolder.adapterPosition, target.adapterPosition
        )
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        (viewHolder as ITouchRemove).removeItem(viewHolder.layoutPosition)
    }

    override fun isLongPressDragEnabled(): Boolean = false

}