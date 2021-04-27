package com.zjl.mytomato.ui.share.sharepage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentMySecondShareBinding
import com.zjl.mytomato.entity.TodoEntity

class MySecondSharePageFragment : BaseFragment<FragmentMySecondShareBinding, SharePageVm>() {
    private lateinit var todoEntity: TodoEntity

    companion object {
        fun newInstance(todoEntity: TodoEntity): MySecondSharePageFragment {
            val fragment = MySecondSharePageFragment()
            fragment.arguments = Bundle().apply {
                putParcelable("todoEntity", todoEntity)
            }
            return fragment
        }
    }

    override fun initViewModel() = ViewModelProvider(this).get(SharePageVm::class.java)

    override fun initUi(): FragmentMySecondShareBinding {
        todoEntity = arguments?.getParcelable<TodoEntity>("todoEntity")!!
        return FragmentMySecondShareBinding.inflate(layoutInflater).apply {
            tvShareText.text = if (todoEntity.hour > 0) {
                "我刚刚在MyTomato专注了${todoEntity.hour}小时${todoEntity.minute}分钟，真的很不错。你也一起来吧！"
            } else {
                "我刚刚在MyTomato专注了${todoEntity.minute}分钟，真的很不错。你也一起来吧！"
            }
            Glide.with(context!!).apply {
                load("${Constant.BASE_PIC_URL}${todoEntity.imageUrl}").into(ivBackground)
            }
        }
    }
}