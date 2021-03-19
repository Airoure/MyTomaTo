package com.zjl.mytomato.ui.todo

import com.zjl.mytomato.BaseRepo
import com.zjl.mytomato.entity.AppEntity
import kotlinx.coroutines.CoroutineScope

class WhiteListRepo(coroutineScope: CoroutineScope) : BaseRepo(coroutineScope) {
    fun setWhiteList(whiteList: MutableList<AppEntity>){
        launch(
                block = {

                }
        )
    }
}