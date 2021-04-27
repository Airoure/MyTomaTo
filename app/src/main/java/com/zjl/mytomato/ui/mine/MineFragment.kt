package com.zjl.mytomato.ui.mine

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjl.mytomato.*
import com.zjl.mytomato.adapter.TimedTaskAdapter
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentMineBinding
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.lock.LockActivity
import com.zjl.mytomato.util.CalendarUtil
import com.zjl.mytomato.view.SpacingDecoration
import com.zjl.mytomato.view.TimedTaskDialog
import java.util.*

class MineFragment : BaseFragment<FragmentMineBinding, MineVm>() {
    private lateinit var timedTaskAdapter: TimedTaskAdapter
    private var todoEntities: List<TodoEntity> = mutableListOf()
    private val alarmManager by lazy { activity?.getSystemService(AlarmManager::class.java) }
    override fun initUi(): FragmentMineBinding {
        timedTaskAdapter = TimedTaskAdapter(context!!, object : TimedTaskAdapter.TimeTaskListener {
            override fun onSwitchChange(timedTaskEntity: TimedTaskEntity, switch: Boolean) {
                when (switch) {
                    true -> {
                        timedTaskEntity.enable = true
                        startAlarm(timedTaskEntity)
                        //开始定时任务
                    }
                    false -> {
                        timedTaskEntity.enable = false
                        stopAlarm(timedTaskEntity)
                        //取消定时任务
                    }
                }
                vm.turnTimeTaskEntity(timedTaskEntity)
            }

            override fun onDelete(timedTaskEntity: TimedTaskEntity) {
                stopAlarm(timedTaskEntity)
                vm.deleteTimeTaskEntity(timedTaskEntity)
                if (timedTaskAdapter.isEmpty()) {
                    ui.layoutEmpty.setVisiable()
                }
            }

        })
        timedTaskAdapter.setTimedTaskEntityList(mutableListOf())
        return FragmentMineBinding.inflate(layoutInflater).apply {
            rvTimedTask.apply {
                layoutManager = LinearLayoutManager(context)
                addItemDecoration(SpacingDecoration(10f, 20f, includeVEdge = true))
                adapter = timedTaskAdapter
            }
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_add -> {
                        TimedTaskDialog(context!!, todoEntities) { item ->
                            vm.addTimedTask(item)
                        }.show()
                    }
                }
                true
            }
        }
    }

    private fun stopAlarm(timedTaskEntity: TimedTaskEntity) {
        val bundle = Bundle()
        bundle.putParcelable("todoEntity", timedTaskEntity)
        //PendingIntent在高版本有一个bug，可能算bug，不能传序列化之后的属性，如果传了，就连和他一起传的string都会变null
        val intent = Intent(context, LockActivity::class.java).putExtra("todoBundle", bundle)
        for (i in 0..6) {
            val pendingIntent = PendingIntent.getActivity(
                App.appContext,
                timedTaskEntity.requestCode.toInt() + i,
                intent,
                FLAG_UPDATE_CURRENT
            )
            alarmManager?.cancel(pendingIntent)
        }
    }

    private fun startAlarm(timedTaskEntity: TimedTaskEntity) {
        val bundle = Bundle()
        bundle.putParcelable("timedTaskEntity", timedTaskEntity)
        //PendingIntent在高版本有一个bug，可能算bug，不能传序列化之后的属性，如果传了，就连和他一起传的string都会变null
        val intent = Intent(context, LockActivity::class.java).putExtra("todoBundle", bundle)
        if (timedTaskEntity.isMonday) {
            alarmManager?.setExact(
                AlarmManager.RTC,
                CalendarUtil.getTimeMilled(
                    Calendar.MONDAY,
                    timedTaskEntity.startHour,
                    timedTaskEntity.startMinute
                ),
                PendingIntent.getActivity(
                    App.appContext,
                    timedTaskEntity.requestCode.toInt(),
                    intent,
                    FLAG_UPDATE_CURRENT
                )
            )
        }
        if (timedTaskEntity.isTuesday) {
            alarmManager?.setExact(
                AlarmManager.RTC,
                CalendarUtil.getTimeMilled(
                    Calendar.TUESDAY,
                    timedTaskEntity.startHour,
                    timedTaskEntity.startMinute
                ),
                PendingIntent.getActivity(
                    App.appContext,
                    timedTaskEntity.requestCode.toInt() + 1,
                    intent,
                    FLAG_UPDATE_CURRENT
                )
            )
        }
        if (timedTaskEntity.isWednesday) {
            alarmManager?.setExact(
                AlarmManager.RTC,
                CalendarUtil.getTimeMilled(
                    Calendar.WEDNESDAY,
                    timedTaskEntity.startHour,
                    timedTaskEntity.startMinute
                ),
                PendingIntent.getActivity(
                    App.appContext,
                    timedTaskEntity.requestCode.toInt() + 2,
                    intent,
                    FLAG_UPDATE_CURRENT
                )
            )
        }
        if (timedTaskEntity.isThursday) {
            alarmManager?.setExact(
                AlarmManager.RTC,
                CalendarUtil.getTimeMilled(
                    Calendar.THURSDAY,
                    timedTaskEntity.startHour,
                    timedTaskEntity.startMinute
                ),
                PendingIntent.getActivity(
                    App.appContext,
                    timedTaskEntity.requestCode.toInt() + 3,
                    intent,
                    FLAG_UPDATE_CURRENT
                )
            )
        }
        if (timedTaskEntity.isFriday) {
            alarmManager?.setExact(
                AlarmManager.RTC,
                CalendarUtil.getTimeMilled(
                    Calendar.FRIDAY,
                    timedTaskEntity.startHour,
                    timedTaskEntity.startMinute
                ),
                PendingIntent.getActivity(
                    App.appContext,
                    timedTaskEntity.requestCode.toInt() + 4,
                    intent,
                    FLAG_UPDATE_CURRENT
                )
            )
        }
        if (timedTaskEntity.isSaturday) {
            alarmManager?.setExact(
                AlarmManager.RTC,
                CalendarUtil.getTimeMilled(
                    Calendar.SATURDAY,
                    timedTaskEntity.startHour,
                    timedTaskEntity.startMinute
                ),
                PendingIntent.getActivity(
                    App.appContext,
                    timedTaskEntity.requestCode.toInt() + 5,
                    intent,
                    FLAG_UPDATE_CURRENT
                )
            )
        }
        if (timedTaskEntity.isSunday) {
            alarmManager?.setExact(
                AlarmManager.RTC,
                CalendarUtil.getTimeMilled(
                    Calendar.SUNDAY,
                    timedTaskEntity.startHour,
                    timedTaskEntity.startMinute
                ),
                PendingIntent.getActivity(
                    App.appContext,
                    timedTaskEntity.requestCode.toInt() + 6,
                    intent,
                    FLAG_UPDATE_CURRENT
                )
            )
        }
    }

    override fun initViewModel(): MineVm {
        return ViewModelProvider(this).get(MineVm::class.java)
    }

    override fun subscribe() {
        vm.todoEntities.observe(this, {
            todoEntities = it
        })
        vm.messageLiveData.observe(this, {
            if (it == Constant.ADD_TIMED_TASK_SUCCESS) {
                Toast.makeText(context, "添加定时任务成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "该时间段已存在任务", Toast.LENGTH_SHORT).show()
            }
            //alarmManager.setRepeating()
            //设置定时任务

        })
        vm.initTimedTaskEntities.observe(this, {
            timedTaskAdapter.setTimedTaskEntityList(it as MutableList<TimedTaskEntity>)
            if (!it.isNullOrEmpty()) {
                ui.layoutEmpty.setGone()
            } else {
                ui.layoutEmpty.setVisiable()
            }
        })
        vm.addedTimedTaskEntity.observe(this, {
            timedTaskAdapter.addTimedTaskEntity(it)
            ui.layoutEmpty.setGone()
        })
    }
}