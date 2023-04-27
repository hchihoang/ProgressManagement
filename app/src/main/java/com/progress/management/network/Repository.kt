package com.progress.management.network

import com.progress.management.base.entity.BaseListLoadMoreResponse
import com.progress.management.base.entity.BaseObjectResponse
import com.progress.management.entity.LoginRequest
import com.progress.management.entity.LoginResponse
import com.progress.management.entity.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(val apiInterface: ApiInterface) {
    fun getData(pageIndex:Int): Single<BaseListLoadMoreResponse<User>> {
        return apiInterface.getDataUser("f",pageIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(username:String,password:String): Single<BaseObjectResponse<LoginResponse>> {
        val loginRequest = LoginRequest()
        loginRequest.username = username
        loginRequest.password = password
        return apiInterface.login(loginRequest)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}