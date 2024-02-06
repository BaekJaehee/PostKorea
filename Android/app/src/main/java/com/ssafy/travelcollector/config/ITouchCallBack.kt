package com.ssafy.travelcollector.config

interface ITouchMove{
    fun moveItem(from: Int, to: Int)
}

interface ITouchRemove{
    fun removeItem(position: Int)
}