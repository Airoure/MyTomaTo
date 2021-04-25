package com.zjl.mytomato.ui.share

import android.content.Context
import android.content.Intent
import com.zjl.mytomato.BaseActivity
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.ActivityShareBinding
import com.zjl.mytomato.entity.TodoEntity

class ShareActivity : BaseActivity<ActivityShareBinding>() {
    override fun initUI() = ActivityShareBinding.inflate(layoutInflater)

    override fun addFragment() {
        val todoEntity = intent.getParcelableExtra<TodoEntity>("todoEntity")!!
        supportFragmentManager.beginTransaction().add(R.id.container, ShareFragment.newInstance(todoEntity)).commit()
    }

    companion object {
        fun open(context: Context, todoEntity: TodoEntity) {
            context.startActivity(Intent(context, ShareActivity::class.java).putExtra("todoEntity", todoEntity))
        }
    }
}