package com.zjl.mytomato.ui.me

import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.*
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentMeBinding
import com.zjl.mytomato.entity.FinishTodoEntity
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.login.LoginActivity
import com.zjl.mytomato.util.SpUtil
import com.zjl.mytomato.view.ColorPickerDialog
import com.zjl.mytomato.view.CommonDialog
import com.zjl.mytomato.view.SetMaxExitTimeDialog
import com.zjl.mytomato.view.SetTomatoTimeDialog

class MeFragment : BaseFragment<FragmentMeBinding, MeViewModel>() {
    private var username: String = ""
    private var localTodoEntities = mutableSetOf<TodoEntity>()
    private var localFinishTodoEntities = mutableSetOf<FinishTodoEntity>()
    private var localTimedTodoEntities = mutableSetOf<TimedTaskEntity>()
    override fun initViewModel() = ViewModelProvider(this).get(MeViewModel::class.java)

    override fun initUi(): FragmentMeBinding {

        return FragmentMeBinding.inflate(layoutInflater).apply {
            vChangeThemeColor.setOnClickListener {
                ColorPickerDialog(context!!) { color ->
                    changeTheme(color)
                }.show()
            }
            vUpdateToNetwork.setOnClickListener {
                if (username.isBlank()) {
                    toast("请先登入")
                    LoginActivity.open(it.context)
                } else {
                    vm.updateToNetWork(
                            localTodoEntities,
                            localFinishTodoEntities,
                            localTimedTodoEntities,
                            username
                    )

                }
            }
            vClearNetworkData.setOnClickListener {
                if (username.isBlank()) {
                    toast("请先登入")
                    LoginActivity.open(context!!)
                } else {
                    clearNetworkData()
                }

            }
            vChangeTomatoTime.setOnClickListener {
                SetTomatoTimeDialog(context!!).show()
            }
            vChangeMaxExitTime.setOnClickListener {
                SetMaxExitTimeDialog(context!!).show()
            }
            vLoginArea.setOnClickListener {
                if (SpUtil.getUsername().isEmpty()) {
                    LoginActivity.open(it.context)
                }
            }
            vExit.setOnClickListener {
                CommonDialog(
                        context!!,
                        content = "退出之前请关闭所有定时任务，否则可能导致定时任务错乱",
                        listener = object : CommonDialog.DialogClickListener {
                            override fun onConfirm() {
                                username = ""
                                SpUtil.setUsername("")
                                tvTip.setVisiable()
                                tvAccount.text = "登录账号"
                                vExit.setGone()
                            }
                        }).show()
            }
        }
    }

    private fun clearNetworkData() {
        CommonDialog(
                context!!,
                content = "确定删除云端数据吗",
                touchOutCamcel = true,
                listener = object : CommonDialog.DialogClickListener {
                    override fun onConfirm() {
                        vm.clearNetworkData(username)
                    }
                }).show()

    }

    override fun subscribe() {
        super.subscribe()
        vm.updateLiveData.observe(this, {
            toast("同步成功")
        })
        vm.clearLiveData.observe(this, {
            when (it) {
                Constant.CLEAR_SUCCESS -> {
                    toast("数据删除完毕")
                }
                Constant.CLEAR_NO_DATA -> {
                    toast("云端没有您的数据")
                }
            }
        })
    }


    override fun init() {
        vm.getAllTodo()
        vm.getAllFinishedTodo()
        vm.getAllTimedTodo()
        username = SpUtil.getUsername()
        if (username.isNotBlank()) {
            ui.apply {
                tvTip.setGone()
                tvAccount.text = username
                vExit.setVisiable()
            }
        }
        vm.localTodoList.observe(this, {
            localTodoEntities = it
        })
        vm.localFinishTodoList.observe(this, {
            localFinishTodoEntities = it
        })
        vm.localTimedTodoList.observe(this, {
            localTimedTodoEntities = it
        })
    }


}