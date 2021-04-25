package com.zjl.mytomato.ui.login

import androidx.lifecycle.MutableLiveData
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.entity.User
import com.zjl.mytomato.util.SpUtil

class LoginVm : BaseViewModel() {

    val registerLiveData = MutableLiveData<Int>()
    val loginLiveData = MutableLiveData<Int>()


    fun register(username: String, password: String) {
        showLoading()
        User(username, password).save(object : SaveListener<String>() {
            override fun done(p0: String?, p1: BmobException?) {
                hideLoading()
                if (p1 == null) {
                    registerLiveData.postValue(Constant.REGISTER_SUCCESS)
                    SpUtil.setUsername(username)
                } else {
                    if (p1.errorCode == 401) {
                        registerLiveData.postValue(Constant.REGISTER_FAIL)

                    } else {
                        networkLiveData.postValue(Constant.NETWORK_ERROR)

                    }
                }
            }

        })
    }

    fun login(username: String, password: String) {
        showLoading()
        BmobQuery<User>().apply {
            addWhereEqualTo("username", username)
            addWhereEqualTo("password", password)
            findObjects(object : FindListener<User>() {
                override fun done(p0: MutableList<User>?, p1: BmobException?) {
                    hideLoading()
                    if (p1 == null) {
                        if (p0?.isEmpty() == true) {
                            loginLiveData.postValue(Constant.LOGIN_ERROR)
                        } else {
                            loginLiveData.postValue(Constant.LOGIN_SUCCESS)
                            SpUtil.setUsername(username)
                        }
                    } else {
                        networkLiveData.postValue(Constant.NETWORK_ERROR)
                    }
                }
            })
        }
    }
}