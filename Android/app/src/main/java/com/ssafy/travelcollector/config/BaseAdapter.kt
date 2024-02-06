package com.ssafy.travelcollector.config

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.ssafy.travelcollector.databinding.FragmentMainBinding


abstract class BaseAdapter<T> : ListAdapter<T, BaseAdapter<T>.BaseHolder>(
    object: DiffUtil.ItemCallback<T>(){
        override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return System.identityHashCode(oldItem) == System.identityHashCode(newItem)
        }
        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
            return oldItem == newItem
        }
    }
){

   abstract inner class BaseHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root){
        abstract fun bindInfo(data: T)
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder

    abstract override fun onBindViewHolder(holder: BaseHolder, position: Int)

}

