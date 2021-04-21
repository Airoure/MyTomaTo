package com.zjl.mytomato.ui.todo

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjl.mytomato.*
import com.zjl.mytomato.adapter.TodoRvAdapter
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentTodoBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.util.SpUtil
import com.zjl.mytomato.view.*

class TodoFragment : BaseFragment<FragmentTodoBinding, TodoVm>() {

    private lateinit var adapter: TodoRvAdapter
    override fun initUi(): FragmentTodoBinding {
        adapter = createTodoAdapter()

        return FragmentTodoBinding.inflate(layoutInflater).apply {
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
                    R.id.change_layout -> {
                        when (SpUtil.getTodoLayout()) {
                            Constant.LINEARLAYOUT -> {
                                adapter.setViewType(Constant.GRIDLAYOUT)
                                rvTodo.layoutManager = GridLayoutManager(context, 3)
                                rvTodo.adapter = adapter
                                SpUtil.setTodoLayout(Constant.GRIDLAYOUT)
                            }
                            Constant.GRIDLAYOUT -> {
                                adapter.setViewType(Constant.LINEARLAYOUT)
                                rvTodo.layoutManager = LinearLayoutManager(context)
                                rvTodo.adapter = adapter
                                SpUtil.setTodoLayout(Constant.LINEARLAYOUT)
                            }
                        }
                    }
                }
                true
            }
            when (SpUtil.getTodoLayout()) {
                Constant.LINEARLAYOUT -> {
                    adapter.setViewType(Constant.LINEARLAYOUT)
                    rvTodo.layoutManager = LinearLayoutManager(context)
                    rvTodo.addItemDecoration(
                        SpacingDecoration(
                            10f,
                            20f,
                            includeVEdge = true,
                            includeHEdge = true
                        )
                    )
                    rvTodo.adapter = adapter
                }
                Constant.GRIDLAYOUT -> {
                    adapter.setViewType(Constant.GRIDLAYOUT)
                    rvTodo.layoutManager = GridLayoutManager(context, 3)
                    rvTodo.addItemDecoration(
                        SpacingDecoration(
                            10f,
                            20f,
                            includeVEdge = true,
                            includeHEdge = true
                        )
                    )
                    rvTodo.adapter = adapter
                }
            }

        }
    }

    override fun initViewModel(): TodoVm {
        return ViewModelProvider(this).get(TodoVm::class.java)
    }

    override fun subscribe() {
        vm.messageLiveData.observe(this, Observer {
            when (it) {
                Constant.ADD_TODO_SUCCESS -> {
                    Toast.makeText(context, "待办添加成功", Toast.LENGTH_SHORT).show()
                    ui.layoutEmpty.setGone()
                }
                Constant.ADD_TODO_FAIL, Constant.UPDATE_TODO_FAIL -> {
                    CommonDialog(context!!, content = "已经存在同名待办啦！").show()
                }
            }
        })

        vm.firstLoadLiveData.observe(this, {
            adapter.setTodoEntityList(it)
            if (it.isEmpty()) {
                ui.layoutEmpty.setVisiable()
            } else {
                ui.layoutEmpty.setGone()
            }

        })

        vm.todoLiveData.observe(this, {
            adapter.addTodoEntity(it)
        })
        vm.removeLiveData.observe(this, {
            adapter.removeItem(it)
            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
            if (adapter.getTodoEntityList().isEmpty()) {
                ui.layoutEmpty.setVisiable()
            } else {
                ui.layoutEmpty.setGone()
            }
        })
    }

    fun createTodoAdapter() = TodoRvAdapter(object : TodoRvAdapter.OnAdapterClickListener {
        override fun onDeleteClick(todoEntity: TodoEntity) {
            vm.deleteTodo(todoEntity)
        }

        override fun onSaveClick(todoEntity: TodoEntity) {
            vm.saveTodo(todoEntity)
        }
    })

}