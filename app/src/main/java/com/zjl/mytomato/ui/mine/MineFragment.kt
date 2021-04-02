package com.zjl.mytomato.ui.mine

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jaredrummler.cyanea.Cyanea
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.R
import com.zjl.mytomato.adapter.TimedTaskAdapter
import com.zjl.mytomato.changeTheme
import com.zjl.mytomato.databinding.FragmentMineBinding
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.todo.TodoVm
import com.zjl.mytomato.view.ColorPickerDialog
import com.zjl.mytomato.view.SpacingDecoration
import com.zjl.mytomato.view.TimedTaskDialog

class MineFragment : BaseFragment<FragmentMineBinding, MineVm>() {
    private lateinit var timedTaskAdapter: TimedTaskAdapter
    private var todoEntities: List<TodoEntity> = mutableListOf()
    override fun initUi(): FragmentMineBinding {
        timedTaskAdapter = TimedTaskAdapter()
        timedTaskAdapter.setTimedTaskEntityList(mutableListOf())
        return FragmentMineBinding.inflate(layoutInflater).apply {
            ivTomato.apply {
                setOnClickListener {
                    ColorPickerDialog(context!!) { color ->
                        changeTheme(color)
                    }.show()
                }
            }
            rvTimedTask.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(SpacingDecoration(10f, 20f, includeVEdge = true))
                adapter = timedTaskAdapter
            }
            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_add -> {
                        TimedTaskDialog(context!!,todoEntities).show()
                    }
                }
                true
            }
        }
    }

    override fun initViewModel(): MineVm {
        return ViewModelProvider(this).get(MineVm::class.java)
    }

    override fun subscribe() {
        vm.todoEntities.observe(this,{
            todoEntities = it
        })
    }
}