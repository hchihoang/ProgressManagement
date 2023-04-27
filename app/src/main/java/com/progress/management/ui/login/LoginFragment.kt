package com.progress.management.ui.login

import android.os.Handler
import android.os.Looper
import com.progress.management.R
import com.progress.management.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import com.progress.management.ui.home.HomeFragment

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    private val viewModel: LoginViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.login_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        return true
    }

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }
}
