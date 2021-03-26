package com.zjl.mytomato.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zjl.mytomato.R
import com.zjl.mytomato.common.Constant.BASE_PIC_URL
import com.zjl.mytomato.databinding.ItemRvTodoBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.lock.LockActivity
import com.zjl.mytomato.view.TodoCardDialog

class TodoRvAdapter(
        private val onAdapterClickListener: OnAdapterClickListener
) :
        RecyclerView.Adapter<TodoRvAdapter.ViewHolder>() {

    private var todoEntityList: MutableList<TodoEntity> = mutableListOf()

    fun setTodoEntityList(list: MutableList<TodoEntity>) {
        this.todoEntityList = list
        notifyDataSetChanged()
    }

    fun getTodoEntityList() = this.todoEntityList

    fun addTodoEntity(todoEntity: TodoEntity) {
        this.todoEntityList.add(todoEntity)
        notifyItemChanged(todoEntityList.size)
    }

    fun removeItem(todoEntity: TodoEntity) {
        val pos = todoEntityList.indexOf(todoEntity)
        todoEntityList.remove(todoEntity)
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, todoEntityList.size);
    }

    inner class ViewHolder(private val ui: ItemRvTodoBinding) : RecyclerView.ViewHolder(ui.root) {
        fun bind(todoEntity: TodoEntity) {
            ui.apply {
                val time: StringBuilder = StringBuilder()
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
                            .load("${BASE_PIC_URL}${todoEntity.imageUrl}")
                            .placeholder(resources.getDrawable(R.color.black))
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(this)
                }
                tvStart.apply {
                    setOnClickListener {
                        val intent = Intent(context, LockActivity::class.java).putExtra(
                                "todoEntity",
                                todoEntity
                        )
                        LockActivity.open(context, todoEntity)
                    }
                }
                ivBackground.setOnLongClickListener {
                    if (ivDelete.visibility == View.GONE) {
                        ivDelete.visibility = View.VISIBLE
                    } else {
                        ivDelete.visibility = View.GONE
                    }
                    true
                }
                ivBackground.setOnClickListener {
                    TodoCardDialog(it.context, todoEntity) {
                        onAdapterClickListener.onSaveClick(it)
                    }.show()
                }
                ivDelete.setOnClickListener {
                    onAdapterClickListener.onDeleteClick(todoEntity)
                    ivDelete.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemRvTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todoEntityList[position])
    }

    override fun getItemCount() = todoEntityList.size

    interface OnAdapterClickListener {
        fun onDeleteClick(todoEntity: TodoEntity)
        fun onSaveClick(todoEntity: TodoEntity)
    }
}