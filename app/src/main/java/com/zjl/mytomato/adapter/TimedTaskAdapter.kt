package com.zjl.mytomato.adapter

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.zjl.mytomato.databinding.ItemTimedTaskBinding
import com.zjl.mytomato.entity.TimedTaskEntity
import com.zjl.mytomato.setGone
import com.zjl.mytomato.view.CommonDialog
import kotlin.random.Random

class TimedTaskAdapter(private val context: Context, private val listener: TimeTaskListener) :
    RecyclerView.Adapter<TimedTaskAdapter.ViewHolder>() {

    private var timedTaskEntityList: MutableList<TimedTaskEntity> = mutableListOf()
    private val random = Random(1)

    inner class ViewHolder(private val ui: ItemTimedTaskBinding) :
        RecyclerView.ViewHolder(ui.root) {
        @SuppressLint("ClickableViewAccessibility", "SetTextI18n")
        fun bind(timedTaskEntity: TimedTaskEntity) {
            ui.apply {
                tvTitle.text = timedTaskEntity.name
                switchTimedTask.setOnCheckedChangeListener { _, isChecked ->
                    listener.onSwitchChange(timedTaskEntity, isChecked)
                }
                mainCard.setBackgroundColor(
                    Color.rgb(
                        random.nextInt(256),
                        random.nextInt(256),
                        random.nextInt(256)
                    )
                )
                var downPositionX = 0f

                mainCard.setOnTouchListener { v, event ->
                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            downPositionX = event.rawX
                        }
                        MotionEvent.ACTION_MOVE -> {
                            if (event.rawX - downPositionX > 0) {
                                v.translationX = event.rawX - downPositionX
                            }
                        }
                        MotionEvent.ACTION_UP -> {
                            if (v.translationX <= (v.right - v.left) / 2) {
                                val animator =
                                    ObjectAnimator.ofFloat(v, "translationX", v.translationX, 0f)
                                animator.duration = 500
                                animator.interpolator = BounceInterpolator()
                                animator.start()
                            } else {
                                CommonDialog(
                                    context,
                                    content = "确定要删除此任务吗",
                                    listener = object : CommonDialog.DialogClickListener {
                                        override fun onConfirm() {
                                            deleteTimeTaskEntity(timedTaskEntity)
                                            listener.onDelete(timedTaskEntity)
                                            val animator = ObjectAnimator.ofFloat(
                                                v,
                                                "translationX",
                                                v.translationX,
                                                0f
                                            )
                                            animator.duration = 500
                                            animator.interpolator = BounceInterpolator()
                                            animator.start()
                                        }

                                        override fun onCancel() {
                                            val animator = ObjectAnimator.ofFloat(
                                                v,
                                                "translationX",
                                                v.translationX,
                                                0f
                                            )
                                            animator.duration = 500
                                            animator.interpolator = BounceInterpolator()
                                            animator.start()
                                        }
                                    }).show()
                            }

                        }
                    }
                    true
                }
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

    fun deleteTimeTaskEntity(timedTaskEntity: TimedTaskEntity) {
        val pos = timedTaskEntityList.indexOf(timedTaskEntity)
        timedTaskEntityList.remove(timedTaskEntity)
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, timedTaskEntityList.size)
    }

    fun isEmpty() = this.timedTaskEntityList.size == 0

    interface TimeTaskListener {
        fun onSwitchChange(timedTaskEntity: TimedTaskEntity, switch: Boolean)
        fun onDelete(timedTaskEntity: TimedTaskEntity)
    }
}