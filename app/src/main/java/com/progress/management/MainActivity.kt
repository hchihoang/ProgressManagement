package com.progress.management

import com.progress.management.base.BaseActivity
import com.progress.management.ui.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {


    override val layoutResId: Int
        get() = R.layout.activity_main
    override val layoutId: Int
        get() = R.id.container

    override fun initListener() {
    }

    override fun initView() {
        getViewController().addFragment(LoginFragment::class.java)
    }

    override fun initData() {
    }
}
