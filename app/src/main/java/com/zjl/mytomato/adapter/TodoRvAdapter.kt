package com.zjl.mytomato.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.ItemRvTodoBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.Lock.LockActivity
import com.zjl.mytomato.ui.todo.WorkWindow

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
        fun bind(todoEntity: TodoEntity) {
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
                ivBackground.apply {
                    Glide.with(context)
                        .load("https://source.unsplash.com/1600x900/?nature/${todoEntity.imageUrl}")
                        .placeholder(resources.getDrawable(R.color.black))
                        .into(this)
                }
                tvStart.apply {
                    setOnClickListener {
                        LockActivity.open(context,todoEntity)
//                        WorkWindow(context, todoEntity).show()
//                        val intent = Intent(context,LockService::class.java)
//                        intent.putExtra("todoEntity",todoEntity)
//                        context.startService(intent)
                    }
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