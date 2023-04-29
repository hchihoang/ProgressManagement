package com.progress.management.ui.home

import androidx.fragment.app.viewModels
import com.progress.management.R
import com.progress.management.base.BaseFragment
import com.progress.management.extension.onAvoidDoubleClick
import com.progress.management.ui.login.LoginFragment
import com.progress.management.ui.progress.ProgressFragment
import com.progress.management.utils.DialogUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun backFromAddFragment() {

    }

    override val layoutId: Int
        get() = R.layout.home_fragment

    override fun initView() {

    }

    override fun initData() {

    }

    override fun initListener() {
        cv_progress.onAvoidDoubleClick {
            getVC().addFragment(ProgressFragment::class.java)
        }
        btn_logout.onAvoidDoubleClick {
            DialogUtil.displayDialog(requireContext(), getString(R.string.str_title_logout),
                getString(R.string.str_message_logout),
                {
                    viewModel.logout()
                },
                {}
            )
        }
        viewModel.logoutLiveData.observe(this) {
            handleObjectResponse(it)
        }
    }

    override fun backPressed(): Boolean {
        return true
    }

    override fun <U> getObjectResponse(data: U) {
        getVC().replaceFragment(LoginFragment::class.java)
    }
}