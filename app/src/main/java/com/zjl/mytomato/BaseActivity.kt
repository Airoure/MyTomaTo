package com.zjl.mytomato

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.jaredrummler.cyanea.app.CyaneaAppCompatActivity

abstract class BaseActivity<V : ViewBinding> : CyaneaAppCompatActivity() {
    protected lateinit var ui: V

    abstract fun initUI(): V
    open fun addFragment() {}

    protected open fun init() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = initUI()
        setContentView(ui.root)
        addFragment()

    }

    override fun onStart() {
        super.onStart()
        init()
    }
}