package com.zjl.mytomato

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zjl.mytomato.view.LoadingDialog

open class BaseViewModel : ViewModel() {
    private var loading: LoadingDialog? = null
    val networkLiveData = MutableLiveData<Int>()
    open fun load() {

    }

    open fun onceLoad() {

    }

    fun setLoadingView(loadingDialog: LoadingDialog) {
        this.loading = loadingDialog
    }

    protected fun showLoading() {
        loading?.show()
    }

    protected fun hideLoading() {
        loading?.hide()
    }


}