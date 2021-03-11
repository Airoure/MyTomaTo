package com.zjl.mytomato.ui.mine

import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentMineBinding
import com.zjl.mytomato.ui.todo.TodoVm

class MineFragment :BaseFragment<FragmentMineBinding,TodoVm>(){
    override fun initUi(): FragmentMineBinding {
        return FragmentMineBinding.inflate(layoutInflater).apply {

        }
    }

    override fun initViewModel(): TodoVm {
        return ViewModelProvider(this).get(TodoVm::class.java)
    }
}