package com.zjl.mytomato

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.jaredrummler.cyanea.Cyanea

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

fun Fragment.changeTheme(color: Int) {
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

fun Fragment.toast(msg: String) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(msg: String) {
    Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
}

fun EditText.addTextListener(beforeTextChanged: (() -> Unit)? = null, onTextChanged: (() -> Unit)? = null, afterTextChanged: (() -> Unit)? = null) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged?.invoke()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke()
        }

        override fun afterTextChanged(s: Editable?) {
            afterTextChanged?.invoke()
        }
    })
}
