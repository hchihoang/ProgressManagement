package com.progress.management.ui.login

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.progress.management.R
import com.progress.management.base.BaseFragment
import com.progress.management.base.entity.BaseError
import com.progress.management.entity.response.LoginResponse
import com.progress.management.extension.onAvoidDoubleClick
import com.progress.management.extension.toast
import com.progress.management.ui.home.HomeFragment
import com.progress.management.utils.DeviceUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

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
        btn_login.onAvoidDoubleClick {
            DeviceUtil.hideSoftKeyboard(activity)
            viewModel.login(login_edt_username.getText(), login_edt_password.getText())
            //getVC().replaceFragment(HomeFragment::class.java, null)
        }
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            handleObjectResponse(it)
        }
    }

    override fun handleValidateError(throwable: BaseError?) {
        throwable?.let {
            toast(it.error)
        }
    }
    override fun <U> getObjectResponse(data: U) {
        if(data is LoginResponse && !data.maNV.isNullOrEmpty()) {
            getVC().replaceFragment(HomeFragment::class.java, null)
        }else{
            toast(getString(R.string.str_login_false))
        }
    }
}
