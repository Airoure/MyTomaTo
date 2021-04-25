package com.zjl.mytomato.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.zjl.mytomato.R
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.common.Constant.BASE_PIC_URL
import com.zjl.mytomato.databinding.ItemRvTodoBinding
import com.zjl.mytomato.databinding.ItemRvTodoGrideBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.lock.LockActivity
import com.zjl.mytomato.view.TodoCardDialog

@Suppress("DEPRECATION")
class TodoRvAdapter(
        private val onAdapterClickListener: OnAdapterClickListener
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var todoEntityList: MutableList<TodoEntity> = mutableListOf()
    private var viewType = Constant.LINEARLAYOUT

    fun setViewType(viewType: Int) {
        this.viewType = viewType
    }

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
        notifyItemRemoved(pos)
        notifyItemRangeChanged(pos, todoEntityList.size)
    }

    inner class ViewHolderLinear(private val ui: ItemRvTodoBinding) :
            RecyclerView.ViewHolder(ui.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
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
                            .placeholder(getPlaceHolder(layoutPosition, resources))
                            .diskCacheStrategy(DiskCacheStrategy.DATA)
                            .into(this)
                }
                tvStart.apply {
                    setOnClickListener {
                        context.startActivity(
                                Intent(
                                        context,
                                        LockActivity::class.java
                                ).putExtra("todoEntity", todoEntity)
                        )
                        (context as Activity).finish()
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

        @SuppressLint("UseCompatLoadingForDrawables")
        private fun getPlaceHolder(position: Int, resources: Resources): Drawable {

            return when (position % 4) {
                0 -> {
                    resources.getDrawable(R.color.purple_200)
                }
                1 -> {
                    resources.getDrawable(R.color.purple_500)
                }
                2 -> {
                    resources.getDrawable(R.color.material_on_surface_stroke)
                }
                else -> {
                    resources.getDrawable(R.color.white)
                }
            }


        }
    }

    inner class ViewHolderGride(private val ui: ItemRvTodoGrideBinding) :
            RecyclerView.ViewHolder(ui.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
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
                        context.startActivity(
                                Intent(
                                        context,
                                        LockActivity::class.java
                                ).putExtra("todoEntity", todoEntity)
                        )
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (this.viewType) {
            Constant.LINEARLAYOUT -> {
                ViewHolderLinear(
                        ItemRvTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                ViewHolderGride(
                        ItemRvTodoGrideBinding.inflate(
                                LayoutInflater.from(parent.context),
                                parent,
                                false
                        )
                )
            }
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (this.viewType) {
            Constant.LINEARLAYOUT -> {
                (holder as ViewHolderLinear).bind(todoEntityList[position])
            }
            else -> {
                (holder as ViewHolderGride).bind(todoEntityList[position])
            }
        }

    }

    override fun getItemCount() = todoEntityList.size

    interface OnAdapterClickListener {
        fun onDeleteClick(todoEntity: TodoEntity)
        fun onSaveClick(todoEntity: TodoEntity)
    }
}