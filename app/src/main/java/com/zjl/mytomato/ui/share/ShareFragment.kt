package com.zjl.mytomato.ui.share

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentShareBinding
import com.zjl.mytomato.entity.TodoEntity
import com.zjl.mytomato.ui.share.sharepage.MyFirstSharePageFragment
import com.zjl.mytomato.ui.share.sharepage.MySecondSharePageFragment
import com.zjl.mytomato.ui.share.sharepage.MyThirdSharePageFragment
import com.zjl.mytomato.util.ShareUtil
import com.zjl.mytomato.util.ShareUtil.saveBitmap

const val SHARE_CODE = 0x11

class ShareFragment : BaseFragment<FragmentShareBinding, ShareVm>() {
    override fun initViewModel() = ViewModelProvider(this).get(ShareVm::class.java)
    private lateinit var fragmentList: List<Fragment>
    private var todoEntity: TodoEntity? = null

    companion object {
        fun newInstance(todoEntity: TodoEntity): ShareFragment {
            val fragment = ShareFragment()
            fragment.arguments = Bundle().apply {
                putParcelable("todoEntity", todoEntity)
            }
            return fragment
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initUi(): FragmentShareBinding {
        todoEntity = arguments?.getParcelable("todoEntity")
        with(todoEntity!!) {
            fragmentList = listOf(
                    MyFirstSharePageFragment.newInstance(this),
                    MySecondSharePageFragment.newInstance(this),
                    MyThirdSharePageFragment.newInstance(this))
        }
        return FragmentShareBinding.inflate(layoutInflater).apply {
            bannerIndicator.mCellCount = fragmentList.size
            toolbar.setNavigationOnClickListener {
                activity?.finish()
            }
            vpShare.apply {
                adapter = object : FragmentStateAdapter(this@ShareFragment) {
                    override fun getItemCount() = fragmentList.size

                    override fun createFragment(position: Int): Fragment {
                        bannerIndicator.mCurrentPosition = position
                        return fragmentList[position]
                    }
                }
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        bannerIndicator.mCurrentPosition = position
                    }
                })
                offscreenPageLimit = 3
            }
            tvShare.setOnClickListener {
                ShareUtil.getBitmapFromView(vpShare, activity!!) { bitmap ->
                    startActivityForResult(
                            Intent.createChooser(
                                    Intent(Intent.ACTION_SEND).setType("image/*").putExtra(
                                            Intent.EXTRA_STREAM,
                                            saveBitmap(it.context, bitmap, "share")), "分享"
                            ), SHARE_CODE
                    )
                }

            }
        }
    }
}