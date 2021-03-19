package com.zjl.mytomato.view

import android.app.Dialog
import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.recyclerview.widget.GridLayoutManager
import com.zjl.mytomato.R
import com.zjl.mytomato.adapter.WhiteListAdapter
import com.zjl.mytomato.databinding.DialogWhiteListBinding
import com.zjl.mytomato.entity.AppEntity

class WhiteListDialog(context: Context,allAppList: MutableList<AppEntity>,whiteAppList: MutableList<AppEntity>) : Dialog(context, R.style.BaseDialog) {
    private val ui: DialogWhiteListBinding
    private val allAppAdapter: WhiteListAdapter
    private val selectAppAdapter: WhiteListAdapter

    init {
        selectAppAdapter = WhiteListAdapter()
        selectAppAdapter.setAppList(whiteAppList)
        allAppAdapter = WhiteListAdapter()
        allAppAdapter.setAppList(allAppList)
        allAppAdapter.setOnItemChangedListener(object : WhiteListAdapter.OnItemChangedListener {
            override fun onItemRemoved(app: AppEntity) {
                selectAppAdapter.addItem(app)
            }

        })
        selectAppAdapter.setOnItemChangedListener(object : WhiteListAdapter.OnItemChangedListener{
            override fun onItemRemoved(app: AppEntity) {
                allAppAdapter.addItem(app)
            }

        })
        ui = DialogWhiteListBinding.inflate(layoutInflater).apply {
            rvAllApp.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = allAppAdapter
            }
            rvSelectedApp.apply {
                layoutManager = GridLayoutManager(context, 3)
                adapter = selectAppAdapter
            }
            btnConfirm.setOnClickListener {

            }
        }
        setContentView(ui.root)
        setCanceledOnTouchOutside(true)
    }
}