package com.zjl.mytomato.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zjl.mytomato.databinding.ItemRvTodoBinding
import com.zjl.mytomato.entity.TodoEntity
import java.util.*

class TodoRvAdapter() : RecyclerView.Adapter<TodoRvAdapter.ViewHolder>() {

    private var todoEntityList: MutableList<TodoEntity> = mutableListOf()

    fun setTodoEntityList(list: MutableList<TodoEntity>) {
        this.todoEntityList = list
        notifyDataSetChanged()
    }

    fun addTodoEntity(todoEntity: TodoEntity) {
        this.todoEntityList.add(todoEntity)
        notifyItemChanged(todoEntityList.size)
    }

    class ViewHolder(private val ui: ItemRvTodoBinding) : RecyclerView.ViewHolder(ui.root) {
        init {
            ui.tvStart.setOnClickListener {
                Log.e("123", "开始")
            }
            ui.ivBackground.apply {
                val uuid = UUID.randomUUID()
                Log.e("UUID","$uuid")
                Glide.with(context)
                    .load("https://source.unsplash.com/1600x900/?nature/$uuid")
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(this)
            }
        }

        fun bind(todoEntity: TodoEntity) {
            if (todoEntity != null) {
                ui.apply {
                    var time: StringBuilder = StringBuilder()
                    todoEntity.run {
                        if (hour != 0) {
                            time.append(hour).append("时")
                        }
                        time.append(minute).append("分")
                    }
                    tvTime.text = time.toString()
                    tvTitle.text = todoEntity.name

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemRvTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoEntityList[position])
    }

    override fun getItemCount() = todoEntityList.size
}