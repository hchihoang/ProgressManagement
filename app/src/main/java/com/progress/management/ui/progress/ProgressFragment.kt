package com.progress.management.ui.progress

import android.content.pm.ActivityInfo
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.progress.management.R
import com.progress.management.base.BaseFragment
import com.progress.management.base.adapter.ProgressAdapter
import com.progress.management.base.custom.dialog.SelectTimeProgressDialog
import com.progress.management.base.entity.BaseError
import com.progress.management.entity.request.DateRequest
import com.progress.management.entity.response.ProgressResponse
import com.progress.management.extension.flattenToAscii
import com.progress.management.extension.gone
import com.progress.management.extension.onAvoidDoubleClick
import com.progress.management.extension.toast
import com.progress.management.extension.visible
import com.progress.management.utils.Constant
import com.progress.management.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.progress_fragment.header
import kotlinx.android.synthetic.main.progress_fragment.img_icon_close_search_id
import kotlinx.android.synthetic.main.progress_fragment.img_icon_close_search_view
import kotlinx.android.synthetic.main.progress_fragment.input_search_id
import kotlinx.android.synthetic.main.progress_fragment.input_search_project
import kotlinx.android.synthetic.main.progress_fragment.rcv_progress
import java.util.Date
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
        val dataRequest = DateRequest(
            DateUtils.dateToString(Date(), Constant.DATE_FORMAT_1),
            DateUtils.dateToString(Date(), Constant.DATE_FORMAT_1)
        )
        viewModel.getDataProgress(dataRequest)
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
        img_icon_close_search_view.onAvoidDoubleClick {
            input_search_project.setText("")
            img_icon_close_search_view.gone()
        }
        input_search_project.setOnEditorActionListener{
            if(it.isNotEmpty()){
                img_icon_close_search_view.visible()
            }else{
                img_icon_close_search_view.gone()
            }
        }
        input_search_project.setObservableFromView {
            if(input_search_project.getFocus()) {
                input_search_project.hideKeyboard()
                input_search_id.setText("")
                if (it.isNotEmpty()) {
                    img_icon_close_search_view.visible()
                } else {
                    img_icon_close_search_view.gone()
                }
                search(true, it)
            }
        }
        //
        img_icon_close_search_id.onAvoidDoubleClick {
            input_search_id.setText("")
            img_icon_close_search_id.gone()
        }
        input_search_id.setOnEditorActionListener{
            if(it.isNotEmpty()){
                img_icon_close_search_id.visible()
            }else{
                img_icon_close_search_id.gone()
            }
        }
        input_search_id.setObservableFromView {
            if(input_search_id.getFocus()) {
                input_search_id.hideKeyboard()
                input_search_project.setText("")
                if (it.isNotEmpty()) {
                    img_icon_close_search_id.visible()
                } else {
                    img_icon_close_search_id.gone()
                }
                search(false, it)
            }
        }
    }

    private fun search(isNameProject: Boolean, stringSearch: String){
        val result = adapter.getAllItem(ProgressResponse::class.java)
        if(stringSearch.isEmpty()){
            adapter.refresh(convertDataShowAll(result))
            return
        }
        val listProgressResponseResul: List<ProgressResponse> = convertDataSearch(convertDataShowAll(result),
            isNameProject, stringSearch.trim().uppercase().flattenToAscii())
        adapter.refresh(listProgressResponseResul)
    }

    private fun checkItem(listDetailProgressRespons: List<ProgressResponse>,
                          searchNameProject: Boolean, stringSearch: String): Boolean {
        var count = 0
        for (progressResponse in listDetailProgressRespons) {
            if(searchNameProject) {
                if (progressResponse.NAME?.uppercase()?.flattenToAscii()?.contains(stringSearch) == true) {
                    progressResponse.isShow = true
                    convertDataShowAll(progressResponse.listDetailProgressRespons)
                    count ++
                } else {
                    progressResponse.isShow = checkItem(progressResponse.listDetailProgressRespons,
                        searchNameProject, stringSearch)
                    if(progressResponse.isShow)
                        count ++
                }
            }else{
                if (progressResponse.ID_MAY?.uppercase()?.contains(stringSearch) == true) {
                    progressResponse.isShow = true
                    convertDataShowAll(progressResponse.listDetailProgressRespons)
                    count ++
                } else {
                    progressResponse.isShow = checkItem(progressResponse.listDetailProgressRespons,
                        searchNameProject, stringSearch)
                    if(progressResponse.isShow)
                        count ++
                }
            }
        }
        return count > 0
    }

    private fun convertDataSearch(convertDataShowAll: List<ProgressResponse>,
                                  searchNameProject: Boolean, stringSearch: String):
            List<ProgressResponse> {
        for (progressResponse in convertDataShowAll) {
            if(searchNameProject) {
                if (progressResponse.NAME?.uppercase()?.flattenToAscii()?.contains(stringSearch) == true) {
                    progressResponse.isShow = true
                    convertDataShowAll(progressResponse.listDetailProgressRespons)
                } else {
                    progressResponse.isShow = checkItem(progressResponse.listDetailProgressRespons,
                        searchNameProject, stringSearch)
                }
            }else{
                if (progressResponse.ID_MAY?.uppercase()?.contains(stringSearch) == true) {
                    progressResponse.isShow = true
                    convertDataShowAll(progressResponse.listDetailProgressRespons)
                } else {
                    progressResponse.isShow = checkItem(progressResponse.listDetailProgressRespons,
                        searchNameProject, stringSearch)
                }
            }
        }

        return convertDataShowAll
    }

    private fun convertDataShowAll(result: List<ProgressResponse>): List<ProgressResponse> {
        for (progressResponse in result) {
            progressResponse.isShow = true
            if (progressResponse.listDetailProgressRespons.size > 0) {
                convertDataShowAll(progressResponse.listDetailProgressRespons)
            }
        }
        return result
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
