package com.progress.management.network


import com.progress.management.base.entity.BaseListLoadMoreResponse
import com.progress.management.base.entity.BaseObjectResponse
import com.progress.management.entity.LoginRequest
import com.progress.management.entity.LoginResponse
import com.progress.management.entity.User
import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @GET("search1")
    @Headers("Content-Type: application/json", "lang: vi")
    fun getDataUser(
        @Query("s") keyWord: String,
        @Query("page") page: Int
    ): Single<BaseListLoadMoreResponse<User>>

    @POST("/api/auth/login/customer")
    @Headers("Content-Type: application/json")
    fun login(@Body loginRequest:LoginRequest) : Single<BaseObjectResponse<LoginResponse>>
}
