package com.zjl.mytomato.ui.mine

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import com.jaredrummler.cyanea.Cyanea
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.R
import com.zjl.mytomato.changeTheme
import com.zjl.mytomato.databinding.FragmentMineBinding
import com.zjl.mytomato.ui.todo.TodoVm
import com.zjl.mytomato.view.ColorPickerDialog

class MineFragment : BaseFragment<FragmentMineBinding, TodoVm>() {
    override fun initUi(): FragmentMineBinding {
        return FragmentMineBinding.inflate(layoutInflater).apply {
            ivTomato.apply {
                setOnClickListener {
                    ColorPickerDialog(context!!) { color ->
                        changeTheme(color)
                    }.show()
                }
            }
        }
    }

    override fun initViewModel(): TodoVm {
        return ViewModelProvider(this).get(TodoVm::class.java)
    }
}