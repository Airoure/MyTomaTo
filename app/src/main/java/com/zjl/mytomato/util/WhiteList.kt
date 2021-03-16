package com.zjl.mytomato.util

object whiteList {
    private var whiteList: MutableSet<String> = mutableSetOf()

    fun add(packageName: String) = whiteList.add(packageName)

    fun remove(packageName: String) = whiteList.remove(packageName)

    fun isContain(packageName: String) = whiteList.contains(packageName)
}

