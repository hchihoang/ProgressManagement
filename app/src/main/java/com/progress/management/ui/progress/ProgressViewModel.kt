package com.progress.management.ui.progress

import com.progress.management.BaseApplication
import com.progress.management.R
import com.progress.management.base.BaseViewModel
import com.progress.management.base.entity.BaseError
import com.progress.management.base.entity.BaseListResponse
import com.progress.management.entity.request.DateRequest
import com.progress.management.entity.response.ProgressResponse
import com.progress.management.extension.ListResponse
import com.progress.management.network.Connection_Progress
import com.progress.management.network.DataCallback
import com.progress.management.utils.DeviceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor() : BaseViewModel() {
    var dataProgressResponse = ListResponse<ProgressResponse>()
    fun getDataProgress(data: DateRequest) {
        dataProgressResponse.value = BaseListResponse<ProgressResponse>().loading()
        Connection_Progress(data,
            object : DataCallback<List<ProgressResponse>> {
                override fun onConnectSuccess(result: List<ProgressResponse>) {
                    val listProgressResponse: ArrayList<ProgressResponse> = convertData(result)
                    dataProgressResponse.value = BaseListResponse<ProgressResponse>().success(
                        listProgressResponse
                    )
                }

                override fun onConnectFail(result: List<ProgressResponse>?) {
                    val stringError: Int
                    if (!DeviceUtil.hasConnection(BaseApplication.context)) {
                        stringError = R.string.str_error_connect_internet
                    } else {
                        stringError = R.string.str_error_get_data
                    }
                    dataProgressResponse.value = BaseListResponse<ProgressResponse>().error(
                        BaseError(stringError),
                        false
                    )
                }

            }
        ).execute()
    }

    private fun convertData(result: List<ProgressResponse>): ArrayList<ProgressResponse> {
        val listProgressResponse: ArrayList<ProgressResponse> = arrayListOf()
        for (progressResponse in result) {
            if (progressResponse.NHOM == "0" || progressResponse.NHOM.isNullOrEmpty()) {
                listProgressResponse.add(progressResponse)
            }
        }
        if (listProgressResponse.size > 0) {
            addDataItem(listProgressResponse, result)
        }
        return listProgressResponse
    }

    private fun addDataItem(
        listProgressResponse: ArrayList<ProgressResponse>,
        result: List<ProgressResponse>
    ) {
        for (progressResponse in listProgressResponse) {
            for (item in result) {
                if (progressResponse.ID == item.NHOM) {
                    progressResponse.listDetailProgressRespons.add(item)
                }
            }
            if (progressResponse.listDetailProgressRespons.size > 0) {
                addDataItem(progressResponse.listDetailProgressRespons, result)
            }
        }
    }

}
