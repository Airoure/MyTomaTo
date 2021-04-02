package com.zjl.mytomato

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<V : ViewBinding, T : BaseViewModel> : Fragment() {
    protected lateinit var ui: V
    protected lateinit var vm: T


    protected abstract fun initViewModel(): T
    protected abstract fun initUi(): V
    protected open fun init() {}

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        vm = initViewModel()
        ui = initUi()
        subscribe()
        return ui.root
    }

    open fun subscribe() {}

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//    }

    override fun onResume() {
        super.onResume()
        init()
        vm.load()
    }
}