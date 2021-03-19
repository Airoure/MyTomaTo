package com.zjl.mytomato.ui.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class WhiteListVm : ViewModel() {

    private val repo by lazy { WhiteListRepo(viewModelScope) }

    fun getWhiteList(){

    }
}