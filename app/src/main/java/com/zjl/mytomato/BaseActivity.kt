package com.zjl.mytomato

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<V : ViewBinding> : AppCompatActivity() {
    protected lateinit var ui: V

    abstract fun initUI(): V

    protected open fun init() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = initUI()
        setContentView(ui.root)
    }

    override fun onStart() {
        super.onStart()
        init()
    }
}