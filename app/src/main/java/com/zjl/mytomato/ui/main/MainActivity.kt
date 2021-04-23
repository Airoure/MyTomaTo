package com.zjl.mytomato.ui.main

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.snackbar.Snackbar
import com.tencent.bugly.beta.Beta
import com.zjl.mytomato.BaseActivity
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.ActivityMainBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.me.MeFragment
import com.zjl.mytomato.ui.mine.MineFragment
import com.zjl.mytomato.ui.statistics.StatisticFragment
import com.zjl.mytomato.ui.todo.TodoFragment
import com.zjl.mytomato.ui.todolist.TodoListFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var fragmentList: List<Fragment>

    companion object {
        private var todoEntity: TodoEntity? = null
        fun open(context: Context, todoEntity: TodoEntity? = null) {
            context.startActivity(Intent(context, MainActivity::class.java).putExtra("todoEntity", todoEntity))
            this.todoEntity = todoEntity
        }
    }

    override fun initUI(): ActivityMainBinding {
        Beta.checkUpgrade()
        fragmentList =
                listOf(TodoFragment(), TodoListFragment(), StatisticFragment(), MineFragment(), MeFragment())
        return ActivityMainBinding.inflate(layoutInflater).apply {
            if (todoEntity != null) {
                Snackbar.make(container, "恭喜你完成了一次待办", Snackbar.LENGTH_SHORT).setAction("去分享", object : View.OnClickListener {
                    override fun onClick(v: View?) {

                    }
                }).show()
            }
            vpMain.apply {
                adapter = object : FragmentStateAdapter(this@MainActivity) {
                    override fun getItemCount() = fragmentList.size

                    override fun createFragment(position: Int) = fragmentList[position]

                }
                isUserInputEnabled = false
                offscreenPageLimit = 3
            }
            bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_todo -> vpMain.setCurrentItem(0, false)
                    R.id.menu_todo_list -> vpMain.setCurrentItem(1, false)
                    R.id.menu_statistics -> vpMain.setCurrentItem(2, false)
                    R.id.menu_timed_task -> vpMain.setCurrentItem(3, false)
                    R.id.menu_me -> vpMain.setCurrentItem(4, false)
                }
                true
            }
        }
    }


}