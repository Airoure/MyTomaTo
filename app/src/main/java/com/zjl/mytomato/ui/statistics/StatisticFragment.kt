package com.zjl.mytomato.ui.statistics

import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentStatisticBinding
import com.zjl.mytomato.ui.todo.TodoVm
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticVm>() {

    private val calendar = Calendar.getInstance()
    override fun initUi(): FragmentStatisticBinding {
        return FragmentStatisticBinding.inflate(layoutInflater).apply {
            calendar.add(Calendar.DAY_OF_MONTH,0)
            val sdf = SimpleDateFormat("yyyy年MM月dd日")
            var date = sdf.format(calendar.time)
            val today = date
            tvDate.text = date
            vm.getNumByDate(date)
            vm.getTimeByDate(date)
            ivPreviousDay.setOnClickListener {
                calendar.add(Calendar.DAY_OF_MONTH,-1)
                date = sdf.format(calendar.time)
                tvDate.text = date
                vm.getNumByDate(date)
                vm.getTimeByDate(date)
            }
            ivNextDay.setOnClickListener {
                if(today!=date){
                    calendar.add(Calendar.DAY_OF_MONTH,1)
                    date = sdf.format(calendar.time)
                    tvDate.text = date
                    vm.getNumByDate(date)
                    vm.getTimeByDate(date)
                }else{
                    Toast.makeText(context, "无法查看未来的记录哦", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initViewModel(): StatisticVm {
        return ViewModelProvider(this).get(StatisticVm::class.java)
    }

    override fun subscribe() {
        vm.finishTodoNum.observe(this, Observer {
            ui.apply {
                tvCount.text = it.toString()
            }
        })
        vm.totalTime.observe(this, Observer {
            if(it!=null){
                ui.tvTimeLength.text = it.toString()
            }else{
                ui.tvTimeLength.text = "0"
            }
        })
        vm.averageTime.observe(this, Observer {
            if(it!=null){
                ui.tvAvgTimeLength.text = it.toString()
            }else{
                ui.tvAvgTimeLength.text = "0"
            }

        })
        vm.dayNum.observe(this, Observer {
            ui.tvTodayCount.text = it.toString()
        })
        vm.dayTime.observe(this, Observer {
            if(it!=null){
                ui.tvTodayTimeLength.text = it.toString()
            }else{
                ui.tvTodayTimeLength.text = "0"
            }

        })
    }

    override fun init() {
        calendar.add(Calendar.DAY_OF_MONTH,0)
        val sdf = SimpleDateFormat("yyyy年MM月dd日")
        var date = sdf.format(calendar.time)
        vm.getFinishTodoNum()
        vm.getTotalTime()
        vm.getTotalAverageTime()
        vm.getNumByDate(date)
        vm.getTimeByDate(date)
    }
}