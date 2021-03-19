package com.zjl.mytomato.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zjl.mytomato.databinding.ItemDialogWhiteListAllBinding
import com.zjl.mytomato.entity.AppEntity
import com.zjl.mytomato.setOnSafeClickListener

class WhiteListAdapter : RecyclerView.Adapter<WhiteListAdapter.ViewHolder>() {

    private var appList: MutableList<AppEntity> = mutableListOf()
    private var listener: OnItemChangedListener? = null

    fun setAppList(appList: MutableList<AppEntity>) {
        this.appList = appList
    }

    fun setOnItemChangedListener(listener: OnItemChangedListener){
        this.listener = listener
    }

    inner class ViewHolder(private val ui: ItemDialogWhiteListAllBinding) : RecyclerView.ViewHolder(ui.root) {
        fun bind(app: AppEntity) {
            ui.apply {
                ivAppIcon.setImageDrawable(app.icon)
                ivAppIcon.setOnSafeClickListener {
                    val pos = appList.indexOf(app)
                    appList.remove(app)
                    listener?.onItemRemoved(app)
                    notifyItemRemoved(pos)
                    notifyItemRangeChanged(pos,appList.size)
                }
                ivAppIcon.setOnLongClickListener {
                    Toast.makeText(it.context, app.name, Toast.LENGTH_SHORT).show()
                    true
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                ItemDialogWhiteListAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(appList[position])
    }

    override fun getItemCount() = appList.size
    fun addItem(app: AppEntity) {
        appList.add(app)
        notifyItemInserted(appList.size-1)
    }

    interface OnItemChangedListener{
        fun onItemRemoved(app: AppEntity)
    }
}