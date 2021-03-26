package com.zjl.mytomato.ui.statistics

import android.app.Activity
import android.graphics.Color
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import com.jaredrummler.cyanea.Cyanea
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentStatisticBinding
import com.zjl.mytomato.view.ColorPickerDialog
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragment : BaseFragment<FragmentStatisticBinding, StatisticVm>() {

    private val calendar = Calendar.getInstance()
    private val mColors = mutableListOf<Int>()
    override fun initUi(): FragmentStatisticBinding {
        return FragmentStatisticBinding.inflate(layoutInflater).apply {
            calendar.add(Calendar.DAY_OF_MONTH, 0)
            val sdf = SimpleDateFormat("yyyy年MM月dd日")
            var date = sdf.format(calendar.time)
            val today = date
            tvDate.text = date
            ivPreviousDay.setOnClickListener {
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                date = sdf.format(calendar.time)
                tvDate.text = date
                vm.getNumByDate(date)
                vm.getTimeByDate(date)
                vm.getPieChartData(date)
            }
            ivNextDay.setOnClickListener {
                if (today != date) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    date = sdf.format(calendar.time)
                    tvDate.text = date
                    vm.getNumByDate(date)
                    vm.getTimeByDate(date)
                    vm.getPieChartData(date)
                } else {
                    Toast.makeText(context, "无法查看未来的记录哦", Toast.LENGTH_SHORT).show()
                }
            }
            ivTomato.setOnClickListener {
                ColorPickerDialog(context!!) { color ->
                    Cyanea.instance.edit {
                        primary(color)
                        accent(color)
                    }.recreate(activity as Activity)
                }.show()
            }
            pieChart.apply {
                setUsePercentValues(true)
                description.isEnabled = false
                setExtraOffsets(5f, 10f, 5f, 5f)
                dragDecelerationFrictionCoef = 0.95f
                isDrawHoleEnabled = false
                setHoleColor(Color.WHITE)
                setTransparentCircleColor(Color.WHITE)
                setTransparentCircleAlpha(110)
                holeRadius = 58f
                transparentCircleRadius = 61f
                setDrawCenterText(true)
                rotationAngle = 0f
                isRotationEnabled = true
                isHighlightPerTapEnabled = true
                //setOnChartValueSelectedListener(this)
                animateY(1400, Easing.EaseInOutQuad)
                legend.apply {
                    verticalAlignment = Legend.LegendVerticalAlignment.TOP
                    horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                    orientation = Legend.LegendOrientation.VERTICAL
                    setDrawInside(false)
                    xEntrySpace = 7f
                    yEntrySpace = 0f
                    yOffset = 0f
                }
                setEntryLabelColor(Color.WHITE)
                setEntryLabelTextSize(12f)
            }
            barChart.apply {
                //setOnChartValueSelectedListener(this)
                setDrawBarShadow(false)
                setDrawValueAboveBar(true)
                description.isEnabled = false
                setMaxVisibleValueCount(60)
                setPinchZoom(false)
                setDrawGridBackground(false)
                xAxis.apply {
                    position = XAxisPosition.BOTTOM
                    setDrawAxisLine(true)
                    setDrawGridLines(false)
                    granularity = 10f
                }
                axisLeft.apply {
                    setDrawAxisLine(true)
                    setDrawGridLines(true)
                    axisMinimum = 0f
                }
                axisRight.apply {
                    setDrawAxisLine(true)
                    setDrawGridLines(false)
                    axisMinimum = 0f
                }
                setFitBars(true)
                animateY(2500)
                legend.apply {
                    verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                    orientation = Legend.LegendOrientation.HORIZONTAL
                    setDrawInside(false)
                    formSize = 8f
                    xEntrySpace = 4f
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
            if (it != null) {
                ui.tvTimeLength.text = it.toString()
            } else {
                ui.tvTimeLength.text = "0"
            }
        })
        vm.averageTime.observe(this, Observer {
            if (it != null) {
                ui.tvAvgTimeLength.text = it.toString()
            } else {
                ui.tvAvgTimeLength.text = "0"
            }

        })
        vm.dayNum.observe(this, Observer {
            ui.tvTodayCount.text = it.toString()
        })
        vm.dayTime.observe(this, Observer {
            if (it != null) {
                ui.tvTodayTimeLength.text = it.toString()
            } else {
                ui.tvTodayTimeLength.text = "0"
            }

        })
        vm.pieChartDate.observe(this, Observer {
            ui.pieChart.apply {
                data = getPieChartData(it)
                highlightValues(null)
                invalidate()
            }

        })
    }

    private fun getPieChartData(datas: Map<String, Int>?): PieData {
        val entries = mutableListOf<PieEntry>()
        if (datas != null) {
            for (item in datas) {
                entries.add(PieEntry(item.value.toFloat(), item.key))
            }
        }
        val pieDataSet = PieDataSet(entries, "").apply {
            setDrawIcons(false)
            sliceSpace = 3f
            iconsOffset = MPPointF(0f, 40f)
            selectionShift = 5f
            colors = mColors
        }
        return PieData(pieDataSet).apply {
            setValueFormatter(PercentFormatter())
            setValueTextSize(11f)
            setValueTextColor(Color.WHITE)
        }

    }

    override fun init() {
        calendar.add(Calendar.DAY_OF_MONTH, 0)
        val sdf = SimpleDateFormat("yyyy年MM月dd日")
        val date = sdf.format(calendar.time)
        for (c in ColorTemplate.COLORFUL_COLORS) mColors.add(c)
        mColors.add(ColorTemplate.getHoloBlue())
        vm.getFinishTodoNum()
        vm.getTotalTime()
        vm.getTotalAverageTime()
        vm.getNumByDate(date)
        vm.getTimeByDate(date)
        vm.getPieChartData(date)
        vm.getAppUsedTime(date)
    }
}