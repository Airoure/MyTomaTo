package com.zjl.mytomato

import android.view.View

inline fun View.setOnSafeClickListener(crossinline action: (View) -> Unit) {
    var lastClick = 0L
    setOnClickListener {
        val now = System.currentTimeMillis()
        val interval = now - lastClick
        lastClick = now
        if (interval < 800) {
            return@setOnClickListener
        }
        action.invoke(it)
    }
}

fun View.setVisiable(){
    this.visibility = View.VISIBLE
}

fun View.setGone(){
    this.visibility = View.GONE
}