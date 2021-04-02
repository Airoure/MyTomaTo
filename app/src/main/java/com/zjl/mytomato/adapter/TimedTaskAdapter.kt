package com.zjl.mytomato.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zjl.mytomato.databinding.ItemTimedTaskBinding
import com.zjl.mytomato.entity.TimedTaskEntity
import kotlin.random.Random

class TimedTaskAdapter : RecyclerView.Adapter<TimedTaskAdapter.ViewHolder>() {

    private var timedTaskEntityList: MutableList<TimedTaskEntity> = mutableListOf()
    private val random = Random(1)

    inner class ViewHolder(private val ui: ItemTimedTaskBinding) :
        RecyclerView.ViewHolder(ui.root) {
        fun bind(timedTaskEntity: TimedTaskEntity) {
            ui.apply {
                tvTitle.text = timedTaskEntity.name
                mainCard.setBackgroundColor(
                    Color.rgb(
                        random.nextInt(256),
                        random.nextInt(256),
                        random.nextInt(256)
                    )
                )
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
}