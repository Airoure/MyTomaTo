package com.zjl.mytomato.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zjl.mytomato.R
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.ItemTimeLineBinding
import com.zjl.mytomato.entity.FinishTodoEntity

class TimeLineAdapter: RecyclerView.Adapter<TimeLineAdapter.ViewHolder>() {
    private var finishTodoEntityList: List<FinishTodoEntity> = mutableListOf()

    inner class ViewHolder(private val ui: ItemTimeLineBinding): RecyclerView.ViewHolder(ui.root) {
        fun bind(finishTodoEntity: FinishTodoEntity){
             with(ui){
                 ivBackground.apply {
                     Glide.with(context)
                         .load("${Constant.BASE_PIC_URL}${finishTodoEntity.imageUrl}")
                         .placeholder(resources.getDrawable(R.color.black))
                         .diskCacheStrategy(DiskCacheStrategy.DATA)
                         .into(this)
                 }
                 tvTitle.text = finishTodoEntity.name
                 val time: StringBuilder = StringBuilder()
                 finishTodoEntity.run {
                     if (hour != 0) {
                         time.append(hour).append("时")
                     }
                     time.append(minute).append("分")
                 }
                 tvTime.text = time.toString()
                 tvFinishTime.text = finishTodoEntity.finishTime
             }
        }
    }

    fun setFinishTodoEntityList(finishTodoEntityList: List<FinishTodoEntity>){
        this.finishTodoEntityList = finishTodoEntityList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemTimeLineBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(finishTodoEntityList[position])
    }

    override fun getItemCount() = finishTodoEntityList.size
}