package com.progress.management.ui.login

import com.progress.management.BaseApplication
import com.progress.management.R
import com.progress.management.base.BaseViewModel
import com.progress.management.base.entity.BaseError
import com.progress.management.base.entity.BaseObjectResponse
import com.progress.management.entity.request.LoginRequest
import com.progress.management.entity.response.LoginResponse
import com.progress.management.extension.ObjectResponse
import com.progress.management.network.Connection_Login
import com.progress.management.network.DataCallback
import com.progress.management.share_preference.HSBASharePref
import com.progress.management.utils.DeviceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val hsbaSharePref: HSBASharePref) : BaseViewModel() {
    var loginResponse = ObjectResponse<LoginResponse>()
    fun login(userName: String, passWork: String) {
        loginResponse.value = BaseObjectResponse<LoginResponse>().loading()
        if(userName.isBlank() || passWork.isBlank()){
            loginResponse.value = BaseObjectResponse<LoginResponse>().error(
                BaseError(R.string.str_validate_user_pass_work),
                false
            )
            return
        }
        val loginRequest = LoginRequest(userName, passWork)
        Connection_Login(loginRequest,
            object : DataCallback<LoginResponse> {
                override fun onConnectSuccess(result: LoginResponse) {
                    hsbaSharePref.savedUser = result
                    loginResponse.value = BaseObjectResponse<LoginResponse>().success(result)
                }

                override fun onConnectFail(result: LoginResponse?) {
                    val stringError: Int
                    if (!DeviceUtil.hasConnection(BaseApplication.context)) {
                        stringError = R.string.str_error_connect_internet
                    }else {
                        stringError = R.string.str_error_get_data
                    }
                    loginResponse.value = BaseObjectResponse<LoginResponse>().error(
                        BaseError(stringError),
                        false
                    )
                }
            }
        ).execute()
    }

}
