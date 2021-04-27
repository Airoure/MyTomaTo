package com.zjl.mytomato.ui.share.sharepage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.R
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentMyThirdShareBinding
import com.zjl.mytomato.entity.TodoEntity

class MyThirdSharePageFragment : BaseFragment<FragmentMyThirdShareBinding, SharePageVm>() {
    private lateinit var todoEntity: TodoEntity

    companion object {
        fun newInstance(todoEntity: TodoEntity): MyThirdSharePageFragment {
            val fragment = MyThirdSharePageFragment()
            fragment.arguments = Bundle().apply {
                putParcelable("todoEntity", todoEntity)
            }
            return fragment
        }
    }

    override fun initViewModel() = ViewModelProvider(this).get(SharePageVm::class.java)

    override fun initUi(): FragmentMyThirdShareBinding {
        todoEntity = arguments?.getParcelable<TodoEntity>("todoEntity")!!
        return FragmentMyThirdShareBinding.inflate(layoutInflater).apply {
            tvShareText.text = if (todoEntity.hour > 0) {
                "我刚刚在MyTomato专注了${todoEntity.hour}小时${todoEntity.minute}分钟，真的很不错。你也一起来吧！"
            } else {
                "我刚刚在MyTomato专注了${todoEntity.minute}分钟，真的很不错。你也一起来吧！"
            }
            Glide.with(context!!).apply {
                load("${Constant.BASE_PIC_URL}${todoEntity.imageUrl}").into(ivBackground)
                load(R.drawable.qrcode).into(ivQrcode)
            }
        }
    }
}