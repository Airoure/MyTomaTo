package com.zjl.mytomato.ui.todo

import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.FragmentTodoBinding

class TodoFragment :BaseFragment<FragmentTodoBinding>() {
    override fun initUi(): FragmentTodoBinding {
        return FragmentTodoBinding.inflate(layoutInflater).apply {
            toolbar.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menu_add -> {
                        Toast.makeText(activity, "添加", Toast.LENGTH_SHORT).show()
                    }
                }
                true
            }
        }
    }

    override fun init() {

    }



}