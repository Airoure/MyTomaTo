package com.zjl.mytomato.ui.login

import androidx.lifecycle.ViewModelProvider
import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.SaveListener
import com.zjl.mytomato.*
import com.zjl.mytomato.databinding.FragmentLoginBinding
import com.zjl.mytomato.entity.User
import com.zjl.mytomato.util.SpUtil
import com.zjl.mytomato.view.LoadingDialog

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
                val loading = LoadingDialog(context!!)
                if (!isLogin) {
                    if (etPassword.length() < 8) {
                        toast("密码长度不足")
                        return@setOnClickListener
                    }
                    if (!etEnsurePassword.text.toString().equals(etPassword.text.toString())) {
                        toast("两次输入的密码不一致")
                        return@setOnClickListener
                    }
                    loading.show()
                    User(etUsername.text.toString(), etPassword.text.toString()).save(object : SaveListener<String>() {
                        override fun done(p0: String?, p1: BmobException?) {
                            loading.dismiss()
                            if (p1 == null) {
                                toast("注册成功，将直接为您登入")
                                SpUtil.setUsername(etUsername.text.toString())
                                activity?.finish()
                            } else {
                                if (p1.errorCode == 401) {
                                    toast("用户名已存在")
                                } else {
                                    toast("网络异常")
                                }
                            }
                        }

                    })
                } else {
                    loading.show()
                    BmobQuery<User>().apply {
                        addWhereEqualTo("username", etUsername.text.toString())
                        addWhereEqualTo("password", etPassword.text.toString())
                        findObjects(object : FindListener<User>() {
                            override fun done(p0: MutableList<User>?, p1: BmobException?) {
                                loading.dismiss()
                                if (p1 == null) {
                                    if (p0?.isEmpty() == true) {
                                        toast("用户名或密码错误，请仔细检察")
                                    } else {
                                        SpUtil.setUsername(etUsername.text.toString())
                                        toast("登入成功")
                                        activity?.finish()
                                    }
                                } else {
                                    toast("网络异常")
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    private fun setTvLoginEnable() {
        ui.apply {
            if (isLogin) {
                tvLogin.isEnabled = etUsername.text.isNotEmpty() && etPassword.text.isNotEmpty()
            } else {
                tvLogin.isEnabled = etUsername.text.isNotEmpty() && etPassword.text.isNotEmpty() && etEnsurePassword.text.isNotEmpty()
            }

        }
    }
}