package com.progress.management

import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.progress.management.base.BaseActivity
import com.progress.management.extension.onAvoidDoubleClick
import com.progress.management.ui.home.HomeFragment
import com.progress.management.ui.login.LoginFragment
import com.progress.management.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val viewModel: LoginViewModel by viewModels()

    override val layoutResId: Int
        get() = R.layout.activity_main
    override val layoutId: Int
        get() = R.id.container

    override fun initListener() {

    }

    override fun initView() {
        if(viewModel.isLogin())
            getViewController().addFragment(HomeFragment::class.java)
        else
            getViewController().addFragment(LoginFragment::class.java)
    }

    override fun initData() {
    }
}
