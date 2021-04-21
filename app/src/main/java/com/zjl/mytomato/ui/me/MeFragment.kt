package com.zjl.mytomato.ui.me

import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.changeTheme
import com.zjl.mytomato.databinding.FragmentMeBinding
import com.zjl.mytomato.view.ColorPickerDialog
import com.zjl.mytomato.view.SetTomatoTimeDialog

class MeFragment : BaseFragment<FragmentMeBinding,MeViewModel>() {
    override fun initViewModel() = ViewModelProvider(this).get(MeViewModel::class.java)

    override fun initUi(): FragmentMeBinding {
        return FragmentMeBinding.inflate(layoutInflater).apply {
            vChangeThemeColor.setOnClickListener {
                ColorPickerDialog(context!!) { color ->
                    changeTheme(color)
                }.show()
            }
            vChangeTomatoTime.setOnClickListener {
                SetTomatoTimeDialog(context!!).show()
            }
            vLoginArea.setOnClickListener {

            }
        }
    }
}