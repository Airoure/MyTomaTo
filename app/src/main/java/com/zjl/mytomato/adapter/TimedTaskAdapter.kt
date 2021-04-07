package com.zjl.mytomato.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zjl.mytomato.databinding.ItemTimedTaskBinding
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.setGone
import kotlin.random.Random

class TimedTaskAdapter(private val onSwitchChange: (TimedTaskEntity,Boolean)->Unit) : RecyclerView.Adapter<TimedTaskAdapter.ViewHolder>() {

    private var timedTaskEntityList: MutableList<TimedTaskEntity> = mutableListOf()
    private val random = Random(1)

    //    getSystemService(AlarmManager::class.java).setExact(AlarmManager.RTC,System.currentTimeMillis()+100000, PendingIntent.getActivity(applicationContext,0,Intent(applicationContext,MainActivity::class.java),0))
    inner class ViewHolder(private val ui: ItemTimedTaskBinding) :
        RecyclerView.ViewHolder(ui.root) {
        fun bind(timedTaskEntity: TimedTaskEntity) {
            ui.apply {
                tvTitle.text = timedTaskEntity.name
                switchTimedTask.setOnCheckedChangeListener { _, isChecked ->
                    onSwitchChange(timedTaskEntity,isChecked)
                }
                mainCard.setBackgroundColor(
                    Color.rgb(
                        random.nextInt(256),
                        random.nextInt(256),
                        random.nextInt(256)
                    )
                )
                with(timedTaskEntity) {
                    switchTimedTask.isChecked = enable
                    tvTime.text =
                        "${startHour}点${startMinute}分~${(startHour + hour + (startMinute + minute) / 60) % 24}点${(startMinute + minute) % 60}分"
                    if (!isMonday) {
                        tvMonday.setGone()
                    }
                    if (!isTuesday) {
                        tvTuesday.setGone()
                    }
                    if (!isWednesday) {
                        tvWednesday.setGone()
                    }
                    if (!isThursday) {
                        tvThursday.setGone()
                    }
                    if (!isFriday) {
                        tvFriday.setGone()
                    }
                    if (!isSaturday) {
                        tvSaturday.setGone()
                    }
                    if (!isSunday) {
                        tvSunday.setGone()
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTimedTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(timedTaskEntityList[position])
    }

    override fun getItemCount() = timedTaskEntityList.size

    fun setTimedTaskEntityList(list: MutableList<TimedTaskEntity>) {
        this.timedTaskEntityList = list
        notifyDataSetChanged()
    }

    fun addTimedTaskEntity(timedTaskEntity: TimedTaskEntity) {
        this.timedTaskEntityList.add(timedTaskEntity)
        notifyItemChanged(timedTaskEntityList.size)
    }
}