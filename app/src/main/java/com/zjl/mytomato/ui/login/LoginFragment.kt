package com.zjl.mytomato.ui.login

import androidx.lifecycle.ViewModelProvider
import com.zjl.mytomato.*
import com.zjl.mytomato.common.Constant
import com.zjl.mytomato.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginVm>() {
    override fun initViewModel() = ViewModelProvider(this).get(LoginVm::class.java)
    private var isLogin = true
    override fun initUi(): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater).apply {
            switchRegisterLogin.setOnCheckedChangeListener { _, isChecked ->
                isLogin = !isChecked
                if (isLogin) {
                    groupEnsurePassword.setGone()
                    toolbar.title = "登入"
                    tvLogin.text = "登入"
                    etEnsurePassword.setText("")
                } else {
                    groupEnsurePassword.setVisiable()
                    toolbar.title = "注册"
                    tvLogin.text = "注册"
                }
                setTvLoginEnable()
            }
            toolbar.setNavigationOnClickListener {
                activity?.finish()
            }
            etUsername.addTextListener(
                onTextChanged = {
                    if (etUsername.length() > 0) {
                        vClearUsername.setVisiable()
                    } else {
                        vClearUsername.setGone()
                    }
                },
                afterTextChanged = { setTvLoginEnable() }
            )
            etPassword.addTextListener(
                onTextChanged = {
                    if (etPassword.length() > 0) {
                        vClearPassword.setVisiable()
                    } else {
                        vClearPassword.setGone()
                    }
                },
                afterTextChanged = { setTvLoginEnable() }
            )
            etEnsurePassword.addTextListener(
                afterTextChanged = { setTvLoginEnable() }
            )
            vClearUsername.setOnClickListener {
                etUsername.setText("")
            }
            vClearPassword.setOnClickListener {
                etPassword.setText("")
            }
            tvLogin.setOnClickListener {
                if (!isLogin) {
                    if (etPassword.length() < 8) {
                        toast("密码长度不足")
                        return@setOnClickListener
                    }
                    if (!etEnsurePassword.text.toString().equals(etPassword.text.toString())) {
                        toast("两次输入的密码不一致")
                        return@setOnClickListener
                    }
                    vm.register(etUsername.text.toString(), etPassword.text.toString())
                } else {
                    vm.login(etUsername.text.toString(), etPassword.text.toString())

                }
            }
        }
    }

    override fun subscribe() {
        super.subscribe()
        vm.registerLiveData.observe(this, {
            when (it) {
                Constant.REGISTER_SUCCESS -> {
                    toast("注册成功，将直接为您登入")
                    activity?.finish()
                }
                Constant.REGISTER_FAIL -> {
                    toast("用户名已存在")
                }
            }
        })
        vm.loginLiveData.observe(this, {
            when (it) {
                Constant.LOGIN_SUCCESS -> {
                    toast("登入成功")
                    activity?.finish()
                }
                Constant.LOGIN_ERROR -> {
                    toast("用户名或密码错误，请仔细检察")
                }
            }
        })

    }

    private fun setTvLoginEnable() {
        ui.apply {
            if (isLogin) {
                tvLogin.isEnabled = etUsername.text.isNotEmpty() && etPassword.text.isNotEmpty()
            } else {
                tvLogin.isEnabled =
                    etUsername.text.isNotEmpty() && etPassword.text.isNotEmpty() && etEnsurePassword.text.isNotEmpty()
            }

        }
    }
}