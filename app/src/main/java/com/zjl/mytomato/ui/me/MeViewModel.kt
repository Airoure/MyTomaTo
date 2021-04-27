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
import com.zjl.mytomato.entity.*
import com.zjl.mytomato.ui.mine.MineRepo
import com.zjl.mytomato.ui.todo.TodoRepo
import com.zjl.mytomato.ui.todolist.TodoListRepo
import com.zjl.mytomato.util.CalendarUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MeViewModel : BaseViewModel() {
    private val todoRepo by lazy { TodoRepo(viewModelScope) }
    private val finishWorkRepo by lazy { TodoListRepo(viewModelScope) }
    private val timedRepo by lazy { MineRepo(viewModelScope) }

    val localTodoList = MutableLiveData<MutableSet<TodoEntity>>()
    val localFinishTodoList = MutableLiveData<MutableSet<FinishTodoEntity>>()
    val localTimedTodoList = MutableLiveData<MutableSet<TimedTaskEntity>>()
    val updateLiveData = MutableLiveData<Int>()
    val clearLiveData = MutableLiveData<Int>()
    val focusWeekTime = MutableLiveData<Map<String, Int>>()
    private val messageLiveData = MutableLiveData<Int>()
    private val todoLiveData = MutableLiveData<TodoEntity>()
    private val addedTimedTaskEntity = MutableLiveData<TimedTaskEntity>()

    fun getAllTodo() {
        todoRepo.getAllTodoInset(localTodoList)
    }

    fun getAllTimedTodo() {
        timedRepo.getAllTimedTaskSet(localTimedTodoList)
    }

    fun addTodo(todoEntity: TodoEntity) {
        todoRepo.addTodo(todoEntity, messageLiveData, todoLiveData)
    }

    fun addFinidhTodo(finishTodoEntity: FinishTodoEntity) {
        finishWorkRepo.addFinishTodo(finishTodoEntity)
    }

    fun addTimedTodo(timedTaskEntity: TimedTaskEntity) {
        timedRepo.addTimedTask(timedTaskEntity, messageLiveData, addedTimedTaskEntity)
    }

    fun clearNetworkData(username: String) {
        showLoading()
        innerClearTodoEntity(username)
        innerClearFinishTodoEntity(username)
        innerClearTimedTodoEntity(username)

    }

    fun getWeekFocusTime() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                finishWorkRepo.getFocusTimeByDate(CalendarUtil.getAllThisWeekDay(), focusWeekTime)
            }
        }
    }

    private fun innerClearFinishTodoEntity(username: String) {
        BmobQuery<NFinishTodoEntity>().addWhereEqualTo("username", username)
            .findObjects(object : FindListener<NFinishTodoEntity>() {
                override fun done(
                    datas: MutableList<NFinishTodoEntity>?,
                    exception: BmobException?
                ) {
                    if (exception != null) {
                        hideLoading()
                        networkLiveData.postValue(Constant.NETWORK_ERROR)
                    } else if (!datas.isNullOrEmpty()) {
                        var size = 0
                        for (data in datas) {
                            NFinishTodoEntity.getInstance()
                                .delete(data.objectId, object : UpdateListener() {
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

    private fun innerClearTimedTodoEntity(username: String) {
        BmobQuery<NTimedTaskEntity>().addWhereEqualTo("username", username)
            .findObjects(object : FindListener<NTimedTaskEntity>() {
                override fun done(
                    datas: MutableList<NTimedTaskEntity>?,
                    exception: BmobException?
                ) {
                    if (exception != null) {
                        hideLoading()
                        networkLiveData.postValue(Constant.NETWORK_ERROR)
                    } else if (!datas.isNullOrEmpty()) {
                        var size = 0
                        for (data in datas) {
                            NTimedTaskEntity.getInstance()
                                .delete(data.objectId, object : UpdateListener() {
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

    private fun innerClearTodoEntity(username: String) {
        BmobQuery<NetworkTodoEntity>().addWhereEqualTo("username", username)
            .findObjects(object : FindListener<NetworkTodoEntity>() {
                override fun done(
                    datas: MutableList<NetworkTodoEntity>?,
                    exception: BmobException?
                ) {
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
                                    }
                                }
                            })
                        }
                    } else {
                        hideLoading()
                    }
                }
            })
    }

    fun updateToNetWork(
        localTodoEntities: MutableSet<TodoEntity>,
        localFinishTodoEntities: MutableSet<FinishTodoEntity>,
        localTimedTaskEntities: MutableSet<TimedTaskEntity>,
        username: String
    ) {
        //同步到网络,先获取网络上的所有数据，再将网络上的数据添加到本地数据的set里面（如果有冲突，网络数据会被删除），再将网络数据全部删除，最后把这个set数据同步到网络和本地
        showLoading()
        innerUpdateToDo(localTodoEntities, username)
        innerUpdateFinishTodo(localFinishTodoEntities, username)
        innerUpdateTimedTaskTodo(localTimedTaskEntities, username)
        //Bmob的查询库有点坑。。似乎不能使用泛型，只能硬编码

    }

    private fun innerUpdateTimedTaskTodo(
        localTimedTaskEntities: MutableSet<TimedTaskEntity>,
        username: String
    ) {
        BmobQuery<NTimedTaskEntity>().addWhereEqualTo("username", username)
            .findObjects(object : FindListener<NTimedTaskEntity>() {
                override fun done(
                    networkdatas: MutableList<NTimedTaskEntity>?,
                    p1: BmobException?
                ) {
                    if (networkdatas != null) {
                        var size = 0
                        for (item in networkdatas) {
                            localTimedTaskEntities.add(item.toLocal())
                            item.delete(object : UpdateListener() {
                                override fun done(p0: BmobException?) {
                                    size++
                                    if (size == networkdatas.size) {
                                        hideLoading()
                                        size = 0
                                        for (item in localTimedTaskEntities) {
                                            item.toNetwork(username)
                                                .save(object : SaveListener<String>() {
                                                    override fun done(
                                                        p0: String?,
                                                        p1: BmobException?
                                                    ) {
                                                        size++
                                                        if (size == localTimedTaskEntities.size) {
                                                            updateLiveData.postValue(Constant.UPDATE_TO_NETWORK_SUCCESS)
                                                        }
                                                    }
                                                })
                                            item.enable = false
                                            this@MeViewModel.addTimedTodo(item)
                                        }
                                    }
                                }
                            })
                        }
                        //网络上没有数据
                        if (networkdatas.size == 0) {
                            hideLoading()
                            size = 0
                            for (item in localTimedTaskEntities) {
                                item.toNetwork(username).save(object : SaveListener<String>() {
                                    override fun done(p0: String?, p1: BmobException?) {
                                        size++
                                        if (size == localTimedTaskEntities.size) {
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

    private fun innerUpdateFinishTodo(
        localFinishTodoEntities: MutableSet<FinishTodoEntity>,
        username: String
    ) {
        BmobQuery<NFinishTodoEntity>().addWhereEqualTo("username", username)
            .findObjects(object : FindListener<NFinishTodoEntity>() {
                override fun done(
                    networkdatas: MutableList<NFinishTodoEntity>?,
                    p1: BmobException?
                ) {
                    if (networkdatas != null) {
                        var size = 0
                        for (item in networkdatas) {
                            localFinishTodoEntities.add(item.toLocal())
                            item.delete(object : UpdateListener() {
                                override fun done(p0: BmobException?) {
                                    size++
                                    if (size == networkdatas.size) {
                                        hideLoading()
                                        size = 0
                                        for (item in localFinishTodoEntities) {
                                            item.toNetwork(username)
                                                .save(object : SaveListener<String>() {
                                                    override fun done(
                                                        p0: String?,
                                                        p1: BmobException?
                                                    ) {
                                                        size++
                                                        if (size == localFinishTodoEntities.size) {
                                                            updateLiveData.postValue(Constant.UPDATE_TO_NETWORK_SUCCESS)
                                                        }
                                                    }
                                                })
                                            this@MeViewModel.addFinidhTodo(item)
                                        }
                                    }
                                }
                            })
                        }
                        //网络上没有数据
                        if (networkdatas.size == 0) {
                            hideLoading()
                            size = 0
                            for (item in localFinishTodoEntities) {
                                item.toNetwork(username).save(object : SaveListener<String>() {
                                    override fun done(p0: String?, p1: BmobException?) {
                                        size++
                                        if (size == localFinishTodoEntities.size) {
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

    private fun innerUpdateToDo(localTodoEntities: MutableSet<TodoEntity>, username: String) {
        BmobQuery<NetworkTodoEntity>().addWhereEqualTo("username", username)
            .findObjects(object : FindListener<NetworkTodoEntity>() {
                override fun done(
                    networkdatas: MutableList<NetworkTodoEntity>?,
                    p1: BmobException?
                ) {
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
                                            item.toNetwork(username)
                                                .save(object : SaveListener<String>() {
                                                    override fun done(
                                                        p0: String?,
                                                        p1: BmobException?
                                                    ) {
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


    fun getAllFinishedTodo() {
        finishWorkRepo.getAllFinishedTodo(localFinishTodoList)
    }
}