package com.progress.management.extension

import androidx.lifecycle.MutableLiveData
import com.progress.management.R
import com.progress.management.base.entity.BaseListLoadMoreResponse
import com.progress.management.base.entity.BaseListResponse
import com.progress.management.base.entity.BaseObjectResponse

typealias ObjectResponse<T> = MutableLiveData<BaseObjectResponse<T>>
typealias ListResponse<T> = MutableLiveData<BaseListResponse<T>>
typealias ListLoadMoreResponse<T> = MutableLiveData<BaseListLoadMoreResponse<T>>

typealias AndroidColors = android.R.color
typealias ProjectColors = R.color
