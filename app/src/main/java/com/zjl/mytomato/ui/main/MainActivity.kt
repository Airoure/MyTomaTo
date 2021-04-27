package com.zjl.mytomato.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
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
import com.zjl.mytomato.ui.share.ShareActivity
import com.zjl.mytomato.ui.statistics.StatisticFragment
import com.zjl.mytomato.ui.todo.TodoFragment
import com.zjl.mytomato.ui.todolist.TodoListFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.samlss.bling.Bling
import me.samlss.bling.BlingType

class MainActivity : BaseActivity<ActivityMainBinding>(), CoroutineScope by MainScope() {

    private lateinit var fragmentList: List<Fragment>

    companion object {
        private var todoEntity: TodoEntity? = null
        fun open(context: Context, todoEntity: TodoEntity? = null) {
            context.startActivity(
                Intent(context, MainActivity::class.java).putExtra(
                    "todoEntity",
                    todoEntity
                )
            )
            this.todoEntity = todoEntity
        }
    }

    override fun initUI(): ActivityMainBinding {
        Beta.checkUpgrade()
        fragmentList =
            listOf(
                TodoFragment(),
                TodoListFragment(),
                StatisticFragment(),
                MineFragment(),
                MeFragment()
            )
        return ActivityMainBinding.inflate(layoutInflater).apply {
            if (todoEntity != null) {
                Snackbar.make(container, "恭喜你完成了一次待办", Snackbar.LENGTH_SHORT)
                    .setAction("去分享") { ShareActivity.open(this@MainActivity, todoEntity!!) }.show()
                val bling = Bling.Builder(window.decorView as ViewGroup)
                    .setDuration(8000)
                    .setShapeCount(66)
                    .setRadiusRange(10f, 20f)
                    .setRotationSpeedRange(-3f, 3f)
                    .setAutoHide(true)
                    .setColors(
                        Color.parseColor("#F44336"),
                        Color.parseColor("#3F51B5"),
                        Color.parseColor("#009688"),
                        Color.parseColor("#E91E63"),
                        Color.parseColor("#2196F3"),
                        Color.parseColor("#5AB963"),
                        Color.parseColor("#FFC107"),
                        Color.parseColor("#9C28B0"),
                        Color.parseColor("#03A9F4"),
                        Color.parseColor("#8BC34A"),
                        Color.parseColor("#FF9800"),
                        Color.parseColor("#673AB7"),
                        Color.parseColor("#00BCD4"),
                        Color.parseColor("#CDDC39"),
                        Color.parseColor("#FF5722")
                    )
                    .setSpeedRange(0.1f, 10f)
                    .setRotationRange(90f, 150f)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .build()
                launch {
                    delay(500)
                    bling.show(BlingType.MIXED)
                }

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