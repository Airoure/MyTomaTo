package com.zjl.mytomato.ui.share.sharepage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.R
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentMyFirstShareBinding
import com.zjl.mytomato.entity.TodoEntity

class MyFirstSharePageFragment() : BaseFragment<FragmentMyFirstShareBinding, SharePageVm>() {
    private lateinit var todoEntity: TodoEntity

    companion object {
        fun newInstance(todoEntity: TodoEntity): MyFirstSharePageFragment {
            val fragment = MyFirstSharePageFragment()
            fragment.arguments = Bundle().apply {
                putParcelable("todoEntity", todoEntity)
            }
            return fragment
        }
    }

    override fun initViewModel() = ViewModelProvider(this).get(SharePageVm::class.java)

    override fun initUi(): FragmentMyFirstShareBinding {
        todoEntity = arguments?.getParcelable<TodoEntity>("todoEntity")!!
        return FragmentMyFirstShareBinding.inflate(layoutInflater).apply {
            Glide.with(context!!).apply {
                load("${Constant.BASE_PIC_URL}${todoEntity.imageUrl}").into(ivBackground)
                load(R.drawable.qrcode).into(ivQrcode)
            }
        }
    }
}