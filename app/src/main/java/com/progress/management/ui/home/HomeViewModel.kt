package com.progress.management.ui.home

import com.progress.management.base.BaseViewModel
import com.progress.management.base.entity.BaseObjectResponse
import com.progress.management.extension.ObjectResponse
import com.progress.management.share_preference.HSBASharePref
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val hsbaSharePref: HSBASharePref) :
    BaseViewModel() {

    var logoutLiveData = ObjectResponse<String>()
    fun logout() {
        hsbaSharePref.logout()
        logoutLiveData.value = BaseObjectResponse<String>().success("")
    }

}