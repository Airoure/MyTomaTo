package com.zjl.mytomato.ui.login

import android.content.Context
import android.content.Intent
import com.zjl.mytomato.BaseActivity
import com.zjl.mytomato.R
import com.zjl.mytomato.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    override fun initUI() = ActivityLoginBinding.inflate(layoutInflater)

    override fun addFragment() {
        supportFragmentManager.beginTransaction().add(R.id.container, LoginFragment()).commit()
    }

    companion object {
        fun open(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}