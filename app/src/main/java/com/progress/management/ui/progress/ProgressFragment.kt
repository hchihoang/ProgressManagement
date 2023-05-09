package com.progress.management.ui.progress

import android.content.pm.ActivityInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.progress.management.R
import com.progress.management.base.BaseFragment
import com.progress.management.base.adapter.ProgressAdapter
import com.progress.management.base.custom.dialog.SelectTimeProgressDialog
import com.progress.management.base.entity.BaseError
import com.progress.management.entity.response.ProgressResponse
import com.progress.management.extension.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.layout_base_recyclerview.view.*
import kotlinx.android.synthetic.main.progress_fragment.header
import kotlinx.android.synthetic.main.progress_fragment.rcv_progress
import javax.inject.Inject


@AndroidEntryPoint
class ProgressFragment : BaseFragment() {
    @Inject
    lateinit var adapter: ProgressAdapter
    private val viewModel: ProgressViewModel by viewModels()
    private val selectTimeProgressDialog: SelectTimeProgressDialog by lazy {
        SelectTimeProgressDialog(
            requireContext()
        )
    }
    override val layoutId: Int
        get() = R.layout.progress_fragment

    override fun backFromAddFragment() {
    }

    override fun backPressed(): Boolean {
        getVC().backFromAddFragment()
        return false
    }

    override fun initView() {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rcv_progress.layoutManager = layoutManager
        rcv_progress.adapter = adapter
    }

    override fun initData() {
        adapter.clear()
        selectData()
    }

    override fun initListener() {
        header.onLeftClick = {
            backPressed()
        }
        header.onRightClick = {
            selectData()
        }
        viewModel.dataProgressResponse.observe(viewLifecycleOwner) {
            handleListResponse(it)
        }
    }

    override fun <U> getListResponse(data: List<U>?) {
        adapter.refresh(data as List<ProgressResponse>)
    }


    private fun selectData() {
        selectTimeProgressDialog.show()
        selectTimeProgressDialog.onClickBtnYes = {
            adapter.clear()
            viewModel.getDataProgress(it)
        }
    }

    override fun handleValidateError(throwable: BaseError?) {
        throwable?.let {
            toast(it.error)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
