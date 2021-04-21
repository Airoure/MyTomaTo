package com.zjl.mytomato.ui.login

import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.BaseFragment
import com.zjl.mytomato.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding,LoginVm>() {
    override fun initViewModel() = ViewModelProvider(this).get(LoginVm::class.java)

    override fun initUi(): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater).apply {

        }
    }
}