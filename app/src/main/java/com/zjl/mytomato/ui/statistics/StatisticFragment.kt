package com.zjl.mytomato.ui.statistics

import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentStatisticBinding
import com.zjl.mytomato.ui.todo.TodoVm

class StatisticFragment : BaseFragment<FragmentStatisticBinding, TodoVm>() {
    override fun initUi(): FragmentStatisticBinding {
        return FragmentStatisticBinding.inflate(layoutInflater).apply {

        }
    }

    override fun initViewModel(): TodoVm {
        return ViewModelProvider(this).get(TodoVm::class.java)
    }
}