package com.zjl.mytomato.ui.me

import androidx.lifecycle.ViewModelProvider
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.zjl.mytomato.*
import com.zjl.mytomato.databinding.FragmentMeBinding
import com.zjl.mytomato.entity.NetworkTodoEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.login.LoginActivity
import com.zjl.mytomato.util.SpUtil
import com.zjl.mytomato.view.*

class MeFragment : BaseFragment<FragmentMeBinding, MeViewModel>() {
    private var username: String? = null
    private var localTodoEntities = mutableSetOf<TodoEntity>()
    private val loading by lazy { LoadingDialog(context!!) }
    override fun initViewModel() = ViewModelProvider(this).get(MeViewModel::class.java)

    override fun initUi(): FragmentMeBinding {

        return FragmentMeBinding.inflate(layoutInflater).apply {
            vChangeThemeColor.setOnClickListener {
                ColorPickerDialog(context!!) { color ->
                    changeTheme(color)
                }.show()
            }
            vUpdateToNetwork.setOnClickListener {
                if (username.isNullOrBlank()) {
                    toast("请先登入")
                    LoginActivity.open(it.context)
                } else {
                    updateToNetWork()

                }
            }
            vClearNetworkData.setOnClickListener {
                clearNetworkData()
            }
            vChangeTomatoTime.setOnClickListener {
                SetTomatoTimeDialog(context!!).show()
            }
            vChangeMaxExitTime.setOnClickListener {
                SetMaxExitTimeDialog(context!!).show()
            }
            vLoginArea.setOnClickListener {
                if (SpUtil.getUsername().isNullOrEmpty()) {
                    LoginActivity.open(it.context)
                }
            }
            vExit.setOnClickListener {
                CommonDialog(context!!, content = "确定要退出吗", listener = object : CommonDialog.DialogClickListener {
                    override fun onConfirm() {
                        username = null
                        SpUtil.setUsername(null)
                        tvTip.setVisiable()
                        tvAccount.text = "登录账号"
                        vExit.setGone()
                    }

                    override fun onCancel() {

                    }
                }).show()
            }
        }
    }

    private fun clearNetworkData() {
        CommonDialog(context!!, content = "确定删除云端数据吗", touchOutCamcel = true, listener = object : CommonDialog.DialogClickListener {
            override fun onConfirm() {
                loading.show()
                BmobQuery<NetworkTodoEntity>().addWhereEqualTo("username", username).findObjects(object : FindListener<NetworkTodoEntity>() {
                    override fun done(datas: MutableList<NetworkTodoEntity>?, exception: BmobException?) {
                        if (exception != null) {
                            loading.dismiss()
                            toast("网络异常")
                        } else if (!datas.isNullOrEmpty()) {
                            var size = 0
                            for (data in datas) {
                                data.delete(object : UpdateListener() {
                                    override fun done(p0: BmobException?) {
                                        size++
                                        if (size == datas.size) {
                                            loading.dismiss()
                                            toast("数据删除完毕")
                                        }
                                    }
                                })
                            }
                        } else {
                            loading.dismiss()
                            toast("云端没有您的数据")
                        }
                    }
                })
            }
        }).show()

    }

    private fun updateToNetWork() {
        loading.show()
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
                                    loading.dismiss()
                                    for (item in localTodoEntities) {
                                        item.toNetwork(username!!).save(object : SaveListener<String>() {
                                            override fun done(p0: String?, p1: BmobException?) {

                                            }
                                        })
                                        vm.addTodo(item)
                                    }
                                }
                            }
                        })
                    }
                    //网络上没有数据
                    if (networkdatas.size == 0) {
                        loading.dismiss()
                        for (item in localTodoEntities) {
                            item.toNetwork(username!!).save(object : SaveListener<String>() {
                                override fun done(p0: String?, p1: BmobException?) {

                                }
                            })
                        }
                    }
                } else if (p1 != null) {
                    toast("网络异常")
                    loading.dismiss()
                }
            }
        })
    }

    override fun init() {
        vm.getAllTodo()
        username = SpUtil.getUsername()
        if (!username.isNullOrBlank()) {
            ui.apply {
                tvTip.setGone()
                tvAccount.text = username
                vExit.setVisiable()
            }
        }
        vm.localTodoList.observe(this, {
            localTodoEntities = it
        })
    }


}