package com.zjl.mytomato.ui.todo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.pm.ApplicationInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.R
import com.zjl.mytomato.adapter.TodoRvAdapter
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentTodoBinding
import com.zjl.mytomato.entity.AppEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.view.CommonDialog
import com.zjl.mytomato.view.SetTodoDialog
import com.zjl.mytomato.view.SpacingDecoration
import com.zjl.mytomato.view.WhiteListDialog

class TodoFragment : BaseFragment<FragmentTodoBinding, TodoVm>() {

    private lateinit var adapter: TodoRvAdapter
    private var lastClick = 0L
    private lateinit var whiteListVm: WhiteListVm
    private lateinit var allAppList: MutableList<AppEntity>
    override fun initUi(): FragmentTodoBinding {
        adapter = TodoRvAdapter(object : TodoRvAdapter.OnAdapterClickListener {
            override fun onDeleteClick(todoEntity: TodoEntity) {
                vm.deleteTodo(todoEntity)
            }

            override fun onSaveClick(todoEntity: TodoEntity) {
                vm.saveTodo(todoEntity)
            }
        })
        return FragmentTodoBinding.inflate(layoutInflater).apply {
            ivTomato.apply {
                setOnClickListener {
                    val animatorSet = AnimatorSet()
                    val xAnimator = ObjectAnimator.ofFloat(this, "scaleX", 1f, 1.5f, 1f)
                    val yAnimator = ObjectAnimator.ofFloat(this, "scaleY", 1f, 1.5f, 1f)
                    animatorSet.duration = 1000
                    animatorSet.play(xAnimator).with(yAnimator)
                    animatorSet.start()
                }
            }
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_add -> {
                        SetTodoDialog(context!!, object : SetTodoDialog.ButtonClickListener {
                            override fun onConfirmClick(todoEntity: TodoEntity) {
                                vm.addTodo(todoEntity)
                            }

                            override fun onCancelClick() {

                            }

                        }).show()
                    }
                    R.id.menu_white_list -> {
                        if(System.currentTimeMillis()-lastClick>1000){
                            WhiteListDialog(context!!,allAppList, mutableListOf()).show()
                            lastClick = System.currentTimeMillis()
                        }
                    }
                }
                true
            }
            rvTodo.layoutManager = LinearLayoutManager(context)
            rvTodo.addItemDecoration(SpacingDecoration(10f, 20f, includeVEdge = true))
            rvTodo.adapter = adapter
        }
    }

    override fun initViewModel(): TodoVm {
        whiteListVm = ViewModelProvider(this).get(WhiteListVm::class.java)
        return ViewModelProvider(this).get(TodoVm::class.java)
    }

    override fun init() {
        allAppList = getAllApp()
        vm.messageLiveData.observe(this, Observer {
            when (it) {
                Constant.ADD_TODO_SUCCESS -> {
                    Toast.makeText(context, "待办添加成功", Toast.LENGTH_SHORT).show()
                }
                Constant.ADD_TODO_FAIL, Constant.UPDATE_TODO_FAIL -> {
                    CommonDialog(context!!, content = "已经存在同名待办啦！").show()
                }
            }
        })

        vm.firstLoadLiveData.observe(this, Observer {
            adapter.setTodoEntityList(it)
        })

        vm.todoLiveData.observe(this, Observer {
            adapter.addTodoEntity(it)
        })
        vm.removeLiveData.observe(this, Observer {
            adapter.removeItem(it)
            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
        })
    }

    private fun getAllApp(): MutableList<AppEntity> {
        val appList = mutableListOf<AppEntity>()
        val packageInfos = activity!!.packageManager.getInstalledPackages(0)
        for (item in packageInfos) {
            if ((item.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0) {
                appList.add(AppEntity(item.applicationInfo.loadLabel(activity!!.packageManager).toString(), item.applicationInfo.loadIcon(activity!!.packageManager)))
            }

        }
        return appList
    }

}