package com.zjl.mytomato

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment
import com.jaredrummler.cyanea.Cyanea
import com.zjl.mytomato.adapter.TodoRvAdapter
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.todo.TodoFragment

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

fun Fragment.changeTheme(color: Int){
    Cyanea.instance.edit {
        primary(color)
        accent(color)
    }.recreate(activity as Activity)
    activity?.finish()
}

fun View.setVisiable() {
    this.visibility = View.VISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}
