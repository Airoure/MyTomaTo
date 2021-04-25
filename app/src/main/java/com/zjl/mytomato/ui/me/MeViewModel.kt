package com.zjl.mytomato.ui.me

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.zjl.mytomato.BaseViewModel
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.entity.NetworkTodoEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.todo.TodoRepo

class MeViewModel : BaseViewModel() {
    private val repo by lazy { TodoRepo(viewModelScope) }
    val localTodoList = MutableLiveData<MutableSet<TodoEntity>>()
    val updateLiveData = MutableLiveData<Int>()
    val clearLiveData = MutableLiveData<Int>()
    private val messageLiveData = MutableLiveData<Int>()
    private val todoLiveData = MutableLiveData<TodoEntity>()

    fun getAllTodo() {
        repo.getAllTodoInset(localTodoList)
    }

    fun addTodo(todoEntity: TodoEntity) {
        repo.addTodo(todoEntity, messageLiveData, todoLiveData)
    }

    fun clearNetworkData(username: String) {
        showLoading()
        BmobQuery<NetworkTodoEntity>().addWhereEqualTo("username", username).findObjects(object : FindListener<NetworkTodoEntity>() {
            override fun done(datas: MutableList<NetworkTodoEntity>?, exception: BmobException?) {
                if (exception != null) {
                    hideLoading()
                    networkLiveData.postValue(Constant.NETWORK_ERROR)
                } else if (!datas.isNullOrEmpty()) {
                    var size = 0
                    for (data in datas) {
                        data.delete(object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                size++
                                if (size == datas.size) {
                                    hideLoading()
                                    clearLiveData.postValue(Constant.CLEAR_SUCCESS)
                                }
                            }
                        })
                    }
                } else {
                    hideLoading()
                    clearLiveData.postValue(Constant.CLEAR_NO_DATA)
                }
            }
        })
    }

    fun updateToNetWork(localTodoEntities: MutableSet<TodoEntity>, username: String) {
        showLoading()
        //同步到网络,先获取网络上的所有数据，再将网络上的数据添加到本地数据的set里面（如果有冲突，网络数据会被删除），再将网络数据全部删除，最后把这个set数据同步到网络和本地
        BmobQuery<NetworkTodoEntity>().addWhereEqualTo("username", username).findObjects(object : FindListener<NetworkTodoEntity>() {
            override fun done(networkdatas: MutableList<NetworkTodoEntity>?, p1: BmobException?) {
                if (networkdatas != null) {
                    var size = 0
                    for (item in networkdatas) {
                        localTodoEntities.add(item.toLocal())
                        item.delete(object : UpdateListener() {
                            override fun done(p0: BmobException?) {
                                size++
                                if (size == networkdatas.size) {
                                    hideLoading()
                                    size = 0
                                    for (item in localTodoEntities) {
                                        item.toNetwork(username).save(object : SaveListener<String>() {
                                            override fun done(p0: String?, p1: BmobException?) {
                                                size++
                                                if (size == localTodoEntities.size) {
                                                    updateLiveData.postValue(Constant.UPDATE_TO_NETWORK_SUCCESS)
                                                }
                                            }
                                        })
                                        this@MeViewModel.addTodo(item)
                                    }
                                }
                            }
                        })
                    }
                    //网络上没有数据
                    if (networkdatas.size == 0) {
                        hideLoading()
                        size = 0
                        for (item in localTodoEntities) {
                            item.toNetwork(username).save(object : SaveListener<String>() {
                                override fun done(p0: String?, p1: BmobException?) {
                                    size++
                                    if (size == localTodoEntities.size) {
                                        updateLiveData.postValue(Constant.UPDATE_TO_NETWORK_SUCCESS)
                                    }
                                }
                            })
                        }
                    }
                } else if (p1 != null) {
                    networkLiveData.postValue(Constant.NETWORK_ERROR)
                    hideLoading()
                }
            }
        })
    }
}