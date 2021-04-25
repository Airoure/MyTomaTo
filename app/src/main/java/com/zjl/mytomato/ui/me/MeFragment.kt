package com.zjl.mytomato.ui.me

import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.*
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentMeBinding
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
                    vm.updateToNetWork(localTodoEntities, username!!)

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
                vm.clearNetworkData(username!!)
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